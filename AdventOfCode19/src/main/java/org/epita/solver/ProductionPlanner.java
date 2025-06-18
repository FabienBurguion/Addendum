package org.epita.solver;

import org.epita.models.*;
import org.epita.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static org.epita.Main.numberODifferentResources;
import static org.epita.models.ResourceType.getLastResourceType;
import static org.epita.utils.Utils.accumulateResources;
import static org.epita.utils.Utils.computeWaitTime;

public class ProductionPlanner {
    private final Map<CacheKey, Integer> cache = new HashMap<>();

    public int planProduction(Blueprint bp, int[] maxSpend, int time, int[] bots, int[] resources) {
        if (time == 0) {
            return resources[getLastResourceType()];
        }

        CacheKey key = new CacheKey(time, bots, resources);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        int best = resources[getLastResourceType()] + bots[getLastResourceType()] * time;

        for (int robotType = 0; robotType < bp.size(); robotType++) {
            RobotProductionCost robotProductionCost = bp.getRecipeFor(robotType);
            if (robotType != getLastResourceType() && bots[robotType] >= maxSpend[robotType]) {
                continue;
            }

            int waitTime = computeWaitTime(robotProductionCost, bots, resources);
            if (waitTime < 0 || time - waitTime - 1 <= 0) {
                continue;
            }

            int newTime = time - waitTime - 1;
            int[] newBots = bots.clone();
            int[] newRes = accumulateResources(bots, resources, waitTime + 1);
            newRes = Utils.spendResources(newRes, robotProductionCost);
            newBots[robotType]++;

            for (int i = 0; i < numberODifferentResources - 1; i++) {
                newRes[i] = Math.min(newRes[i], maxSpend[i] * newTime);
            }

            int result = planProduction(bp, maxSpend, newTime, newBots, newRes);
            best = Math.max(best, result);
        }

        cache.put(key, best);
        return best;
    }
}