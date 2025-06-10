package org.epita.service.io;

import org.epita.models.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlueprintParser {
    private static final String FILENAME = "data.txt";

    public static List<Blueprint> parseInput() throws IOException {
        List<Blueprint> blueprints = new ArrayList<>();
        String content = Files.readString(Paths.get(FILENAME)).trim();
        String[] lines = content.split("\\R");
        Pattern pattern = Pattern.compile("\\d+");

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            List<Integer> numbers = new ArrayList<>();
            while (matcher.find()) {
                numbers.add(Integer.parseInt(matcher.group()));
            }

            if (numbers.size() < 10) {
                throw new IllegalArgumentException("Invalid line: " + line);
            }

            List<RobotProductionCost> robotProductionCosts = List.of(
                    new RobotProductionCost(List.of(
                            new Material(numbers.get(1), ResourceType.ORE)
                    )),
                    new RobotProductionCost(List.of(
                            new Material(numbers.get(2), ResourceType.ORE)
                    )),
                    new RobotProductionCost(List.of(
                            new Material(numbers.get(3), ResourceType.ORE),
                            new Material(numbers.get(4), ResourceType.CLAY)
                    )),
                    new RobotProductionCost(List.of(
                            new Material(numbers.get(5), ResourceType.ORE),
                            new Material(numbers.get(6), ResourceType.OBSIDIAN)
                    )),
                    new RobotProductionCost(List.of(
                            new Material(numbers.get(7), ResourceType.GEODE),
                            new Material(numbers.get(8), ResourceType.CLAY),
                            new Material(numbers.get(9), ResourceType.OBSIDIAN)
                    ))
            );

            blueprints.add(new Blueprint(robotProductionCosts));
        }

        return blueprints;
    }
}