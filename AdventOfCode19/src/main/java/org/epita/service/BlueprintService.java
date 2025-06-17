package org.epita.service;

import org.epita.controller.dto.BlueprintOutput;
import org.epita.models.Blueprint;
import org.epita.service.io.BlueprintParser;
import org.epita.solver.Solver;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.epita.utils.Utils.findIndexOfMax;

@Service
public class BlueprintService {
    public List<Blueprint> getBlueprints() throws IOException {
        return BlueprintParser.parseInput();
    }

    public Tuple<List<BlueprintOutput>, Integer> solve(List<Blueprint> blueprints) throws IOException {
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

        System.out.println("Total: " + total);

        int bestBlueprintIndex = findIndexOfMax(values) + 1;
        List<BlueprintOutput> blueprintsRes = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            blueprintsRes.add(new BlueprintOutput(i + 1, values[i]));
        }
        return new Tuple<>(blueprintsRes, bestBlueprintIndex);
    }
}
