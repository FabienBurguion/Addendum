package org.epita.solver;

import org.epita.models.*;
import org.epita.solver.utils.GameUtils;

import java.util.HashMap;
import java.util.Map;

import static org.epita.Main.numberOfResources;
import static org.epita.models.ResourceType.getLastResourceType;

public class ProductionPlanner {
    private final Map<CacheKey, Integer> cache = new HashMap<>();

    public int planProduction(Blueprint bp, int[] maxSpend, int time, int[] bots, int[] res) {
        if (time == 0) {
            return res[getLastResourceType()];
        }

        CacheKey key = new CacheKey(time, bots, res);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        int best = res[getLastResourceType()] + bots[getLastResourceType()] * time;

        for (int robotType = 0; robotType < bp.size(); robotType++) {
            Recipe recipe = bp.getRecipeFor(robotType);
            if (robotType != getLastResourceType() && bots[robotType] >= maxSpend[robotType]) {
                continue;
            }

            int waitTime = computeWaitTime(recipe, bots, res);
            if (waitTime < 0 || time - waitTime - 1 <= 0) {
                continue;
            }

            int newTime = time - waitTime - 1;
            int[] newBots = bots.clone();
            int[] newRes = accumulateResources(bots, res, waitTime + 1);
            newRes = GameUtils.spendResources(newRes, recipe);
            newBots[robotType]++;

            for (int i = 0; i < numberOfResources - 1; i++) {
                newRes[i] = GameUtils.minimum(newRes[i], maxSpend[i] * newTime);
            }

            int result = planProduction(bp, maxSpend, newTime, newBots, newRes);
            best = Math.max(best, result);
        }

        cache.put(key, best);
        return best;
    }

    private int computeWaitTime(Recipe recipe, int[] bots, int[] res) {
        int maxWait = 0;
        for (Ingredient req : recipe.ingredients()) {
            int amount = req.amount();
            int resType = req.resourceType();
            if (bots[resType] == 0) return -1;
            int missing = amount - res[resType];
            if (missing > 0) {
                int wait = (int) Math.ceil((double) missing / bots[resType]);
                maxWait = Math.max(maxWait, wait);
            }
        }
        return maxWait;
    }

    private int[] accumulateResources(int[] bots, int[] res, int duration) {
        int[] newRes = res.clone();
        for (int i = 0; i < numberOfResources; i++) {
            newRes[i] += bots[i] * duration;
        }
        return newRes;
    }
}