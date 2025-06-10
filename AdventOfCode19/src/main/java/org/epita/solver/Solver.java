package org.epita.solver;

import lombok.AllArgsConstructor;
import org.epita.models.Blueprint;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

import static org.epita.solver.utils.GameUtils.computeMaxSpend;

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

            int[] initialBots = {1, 0, 0, 0, 0};
            int[] initialResources = {0, 0, 0, 0, 0};

            ProductionPlanner planner = new ProductionPlanner();
            int geodes = planner.planProduction(blueprint, maxSpend, limit, initialBots, initialResources);
            System.out.printf("Blueprint %d → %d geodes%n", i + 1, geodes);

            int score = scoreFn.apply(i, geodes);
            total = aggregateFn.applyAsInt(total, score);
        }
        return total;
    }
}
