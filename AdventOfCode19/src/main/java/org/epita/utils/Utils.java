package org.epita.utils;

import org.epita.models.Blueprint;
import org.epita.models.Material;
import org.epita.models.RobotProductionCost;

import static org.epita.Main.numberODifferentResources;

public class Utils {
    public static int[] spendResources(int[] res, RobotProductionCost robotProductionCost) {
        int[] newRes = res.clone();
        for (Material material : robotProductionCost.materials()) {
            newRes[material.resourceType()] -= material.amount();
        }
        return newRes;
    }

    public static int[] computeMaxSpend(Blueprint blueprint) {
        int[] maxSpend = new int[numberODifferentResources];
        for (RobotProductionCost robotProductionCost : blueprint.robotProductionCosts()) {
            for (Material req : robotProductionCost.materials()) {
                int amount = req.amount();
                int resourceType = req.resourceType();
                if (resourceType < numberODifferentResources - 1 && amount > maxSpend[resourceType]) {
                    maxSpend[resourceType] = amount;
                }
            }
        }
        return maxSpend;
    }

    public static int computeWaitTime(RobotProductionCost robotProductionCost, int[] bots, int[] res) {
        int maxWait = 0;
        for (Material req : robotProductionCost.materials()) {
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
        for (int i = 0; i < numberODifferentResources; i++) {
            newRes[i] += bots[i] * duration;
        }
        return newRes;
    }

    public static int findIndexOfMax(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Le tableau est vide ou null");
        }

        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
