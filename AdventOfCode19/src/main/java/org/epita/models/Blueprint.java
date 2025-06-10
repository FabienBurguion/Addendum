package org.epita.models;

import java.util.List;

public record Blueprint(List<RobotProductionCost> robotProductionCosts) {

    public RobotProductionCost getRecipeFor(int robotType) {
        return robotProductionCosts.get(robotType);
    }

    public int size() {
        return robotProductionCosts.size();
    }
}
