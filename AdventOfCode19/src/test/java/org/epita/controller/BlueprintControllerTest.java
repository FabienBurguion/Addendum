package org.epita.controller;

import org.epita.controller.dto.BlueprintOutput;
import org.epita.models.Blueprint;
import org.epita.models.Material;
import org.epita.models.ResourceType;
import org.epita.models.RobotProductionCost;
import org.epita.service.BlueprintService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.yaml.snakeyaml.util.Tuple;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BlueprintController.class)
class BlueprintControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlueprintService blueprintService;

    @Test
    void analyseBlueprint_returnsExpectedResult() throws Exception {
        Blueprint dummyBlueprint = new Blueprint(List.of(
                new RobotProductionCost(List.of(new Material(4, ResourceType.ORE))),
                new RobotProductionCost(List.of(new Material(2, ResourceType.ORE))),
                new RobotProductionCost(List.of(
                        new Material(3, ResourceType.ORE),
                        new Material(14, ResourceType.CLAY)
                )),
                new RobotProductionCost(List.of(
                        new Material(2, ResourceType.ORE),
                        new Material(7, ResourceType.OBSIDIAN)
                )),
                new RobotProductionCost(List.of(
                        new Material(3, ResourceType.GEODE),
                        new Material(2, ResourceType.CLAY),
                        new Material(3, ResourceType.OBSIDIAN)
                ))
        ));

        List<BlueprintOutput> expectedOutputs = List.of(
                new BlueprintOutput(1, 2)
        );

        when(blueprintService.getBlueprints()).thenReturn(List.of(dummyBlueprint));
        when(blueprintService.solve(List.of(dummyBlueprint)))
                .thenReturn(new Tuple<>(expectedOutputs, 1));

        mockMvc.perform(get("/blueprints/analyze").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bestBlueprint").value(1))
                .andExpect(jsonPath("$.blueprints", hasSize(1)))
                .andExpect(jsonPath("$.blueprints[0].id").value(1))
                .andExpect(jsonPath("$.blueprints[0].quality").value(2));
    }
}