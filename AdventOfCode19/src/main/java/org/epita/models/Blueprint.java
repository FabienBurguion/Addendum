package org.epita.models;

import java.util.List;

public record Blueprint(List<Recipe> recipes) {

    public Recipe getRecipeFor(int robotType) {
        return recipes.get(robotType);
    }

    public int size() {
        return recipes.size();
    }
}
