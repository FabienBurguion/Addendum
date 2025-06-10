package org.epita.io;

import org.epita.models.Blueprint;
import org.epita.models.ResourceType;
import org.epita.models.RobotProductionCost;
import org.epita.service.io.BlueprintParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlueprintParserTest {

    private static final String TEST_FILE = "data.txt";

    @BeforeEach
    void setUp() throws IOException {
        String line = "Blueprint 1: Each ore robot costs 4 ore. " +
                "Each clay robot costs 2 ore. " +
                "Each obsidian robot costs 3 ore and 14 clay. " +
                "Each geode robot costs 2 ore and 7 obsidian. " +
                "Each diamond robot costs 3 geode, 2 clay and 3 obsidian.";
        Files.writeString(Paths.get(TEST_FILE), line);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    void testParseInput_ReturnsCorrectBlueprintStructure() throws IOException {
        List<Blueprint> blueprints = BlueprintParser.parseInput();

        assertEquals(1, blueprints.size(), "Should contain one blueprint");

        Blueprint blueprint = blueprints.get(0);
        List<RobotProductionCost> costs = blueprint.robotProductionCosts();

        assertEquals(5, costs.size(), "Each blueprint should contain 5 robot production costs");

        // Robot 0 : 4 ore
        assertEquals(1, costs.get(0).materials().size());
        assertEquals(4, costs.get(0).materials().get(0).amount());
        assertEquals(ResourceType.ORE, costs.get(0).materials().get(0).resourceType());

        // Robot 1 : 2 ore
        assertEquals(1, costs.get(1).materials().size());
        assertEquals(2, costs.get(1).materials().get(0).amount());
        assertEquals(ResourceType.ORE, costs.get(1).materials().get(0).resourceType());

        // Robot 2 : 3 ore, 14 clay
        assertEquals(2, costs.get(2).materials().size());
        assertEquals(3, costs.get(2).materials().get(0).amount());
        assertEquals(ResourceType.ORE, costs.get(2).materials().get(0).resourceType());
        assertEquals(14, costs.get(2).materials().get(1).amount());
        assertEquals(ResourceType.CLAY, costs.get(2).materials().get(1).resourceType());

        // Robot 3 : 2 ore, 7 obsidian
        assertEquals(2, costs.get(3).materials().size());
        assertEquals(2, costs.get(3).materials().get(0).amount());
        assertEquals(ResourceType.ORE, costs.get(3).materials().get(0).resourceType());
        assertEquals(7, costs.get(3).materials().get(1).amount());
        assertEquals(ResourceType.OBSIDIAN, costs.get(3).materials().get(1).resourceType());

        // Robot 4 : 3 geode, 2 clay, 3 obsidian
        assertEquals(3, costs.get(4).materials().size());
        assertEquals(3, costs.get(4).materials().get(0).amount());
        assertEquals(ResourceType.GEODE, costs.get(4).materials().get(0).resourceType());
        assertEquals(2, costs.get(4).materials().get(1).amount());
        assertEquals(ResourceType.CLAY, costs.get(4).materials().get(1).resourceType());
        assertEquals(3, costs.get(4).materials().get(2).amount());
        assertEquals(ResourceType.OBSIDIAN, costs.get(4).materials().get(2).resourceType());
    }
}