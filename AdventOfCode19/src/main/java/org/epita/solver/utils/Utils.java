package org.epita.solver.utils;

import org.epita.models.Blueprint;
import org.epita.models.Ingredient;
import org.epita.models.Recipe;

import static org.epita.Main.numberOfResources;

public class Utils {
    public static int[] spendResources(int[] res, Recipe recipe) {
        int[] newRes = res.clone();
        for (Ingredient ingredient : recipe.ingredients()) {
            newRes[ingredient.resourceType()] -= ingredient.amount();
        }
        return newRes;
    }

    public static int[] computeMaxSpend(Blueprint blueprint) {
        int[] maxSpend = new int[numberOfResources];
        for (Recipe recipe : blueprint.recipes()) {
            for (Ingredient req : recipe.ingredients()) {
                int amount = req.amount();
                int resourceType = req.resourceType();
                if (resourceType < numberOfResources - 1 && amount > maxSpend[resourceType]) {
                    maxSpend[resourceType] = amount;
                }
            }
        }
        return maxSpend;
    }

    public static int computeWaitTime(Recipe recipe, int[] bots, int[] res) {
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

    public static int[] accumulateResources(int[] bots, int[] res, int duration) {
        int[] newRes = res.clone();
        for (int i = 0; i < numberOfResources; i++) {
            newRes[i] += bots[i] * duration;
        }
        return newRes;
    }
}
