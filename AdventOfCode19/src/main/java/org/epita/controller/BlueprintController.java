package org.epita.controller;

import org.epita.controller.dto.BlueprintDto;
import org.epita.controller.dto.BlueprintOutput;
import org.epita.models.Blueprint;
import org.epita.service.BlueprintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.Tuple;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/blueprints")
public class BlueprintController {

    private final BlueprintService blueprintService;

    public BlueprintController(BlueprintService blueprintService) {
        this.blueprintService = blueprintService;
    }

    @GetMapping
    @RequestMapping("/analyze")
    public ResponseEntity<BlueprintDto> analyseBlueprint() {
        try {
            List<Blueprint> blueprints = blueprintService.getBlueprints();
            Tuple<List<BlueprintOutput>, Integer> result = blueprintService.solve(blueprints);
            List<BlueprintOutput> blueprintsRes = result._1();
            Integer bestBlueprintIndex = result._2();
            BlueprintDto blueprintDto = new BlueprintDto(bestBlueprintIndex, blueprintsRes);

            return ResponseEntity.ok(blueprintDto);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
