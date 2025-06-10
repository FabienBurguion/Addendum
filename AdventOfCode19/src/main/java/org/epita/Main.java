package org.epita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static int numberODifferentResources = 5;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        /*
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


            Solver solverPart2 = new Solver(
                    blueprints,
                    32,
                    3,
                    (_, geodes) -> geodes,
                    1,
                    (a, b) -> a * b
            );
            System.out.println("Total score part 2: " + solverPart2.run());


        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
         */
    }
}