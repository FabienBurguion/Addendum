package org.epita.solver;

import lombok.AllArgsConstructor;
import org.epita.models.Blueprint;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

import static org.epita.Main.numberOfResources;
import static org.epita.solver.utils.Utils.computeMaxSpend;

@AllArgsConstructor
public class Solver {
    private final List<Blueprint> blueprints;
    private final int limit;
    private final int count;
    private final BiFunction<Integer, Integer, Integer> scoreFn;
    private final int initialTotal;
    private final IntBinaryOperator aggregateFn;

    public int run() {
        int total = initialTotal;
        for (int i = 0; i < Math.min(count, blueprints.size()); i++) {
            Blueprint blueprint = blueprints.get(i);
            int[] maxSpend = computeMaxSpend(blueprint);

            int[] initialBots = new int[numberOfResources];
            int[] initialResources = new int[numberOfResources];
            initialBots[0] = 1;

            ProductionPlanner planner = new ProductionPlanner();
            int diamonds = planner.planProduction(blueprint, maxSpend, limit, initialBots, initialResources);
            System.out.printf("Blueprint %d → %d diamonds%n", i + 1, diamonds);

            int score = scoreFn.apply(i, diamonds);
            total = aggregateFn.applyAsInt(total, score);
        }
        return total;
    }
}
