package org.epita.solver.utils;

import org.epita.models.Blueprint;
import org.epita.models.Ingredient;
import org.epita.models.Recipe;

public class GameUtils {
    public static int minimum(int a, int b) {
        return Math.min(a, b);
    }

    public static int[] spendResources(int[] res, Recipe recipe) {
        int[] newRes = res.clone();
        for (Ingredient ingredient : recipe.ingredients()) {
            newRes[ingredient.resourceType()] -= ingredient.amount();
        }
        return newRes;
    }

    public static int[] computeMaxSpend(Blueprint blueprint) {
        int[] maxSpend = new int[5];
        for (Recipe recipe : blueprint.recipes()) {
            for (Ingredient req : recipe.ingredients()) {
                int amount = req.amount();
                int resourceType = req.resourceType();
                if (resourceType < 4 && amount > maxSpend[resourceType]) {
                    maxSpend[resourceType] = amount;
                }
            }
        }
        return maxSpend;
    }
}
