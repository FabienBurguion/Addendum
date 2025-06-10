package org.epita;

import org.epita.models.Blueprint;
import org.epita.parser.BlueprintParser;
import org.epita.solver.Solver;

import java.io.IOException;
import java.util.List;

public class Main {
    public static int numberODifferentResources = 5;

    public static void main(String[] args) {
        try {
            List<Blueprint> blueprints = BlueprintParser.parseInput();

            Solver solverPart1 = new Solver(
                    blueprints,
                    24,
                    blueprints.size(),
                    (i, geodes) -> (i + 1) * geodes,
                    0,
                    Integer::sum
            );
            System.out.println("Total score part 1: " + solverPart1.run());

            /*
            Solver solverPart2 = new Solver(
                    blueprints,
                    32,
                    3,
                    (_, geodes) -> geodes,
                    1,
                    (a, b) -> a * b
            );
            System.out.println("Total score part 2: " + solverPart2.run());
             */

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
    }
}