package org.epita.controller;

import org.epita.controller.dto.BlueprintDto;
import org.epita.controller.dto.BlueprintOutput;
import org.epita.models.Blueprint;
import org.epita.parser.BlueprintParser;
import org.epita.solver.Solver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.epita.utils.Utils.findIndexOfMax;

@RestController
@RequestMapping("/blueprints")
public class BlueprintController {

    @GetMapping
    @RequestMapping("/analyze")
    public ResponseEntity<BlueprintDto> analyseBlueprint() {
        try {
            List<Blueprint> blueprints = BlueprintParser.parseInput();
            Solver solver = new Solver(
                    blueprints,
                    24,
                    blueprints.size(),
                    (i, geodes) -> (i + 1) * geodes,
                    0,
                    Integer::sum
            );

            Tuple<int[], Integer> result = solver.run();
            int[] values = result._1();
            int total = result._2();

            int bestBlueprintIndex = findIndexOfMax(values) + 1;
            List<BlueprintOutput> blueprintsRes = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                blueprintsRes.add(new BlueprintOutput(i + 1, values[i]));
            }
            BlueprintDto blueprintDto = new BlueprintDto(bestBlueprintIndex, blueprintsRes);

            return ResponseEntity.ok(blueprintDto);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
