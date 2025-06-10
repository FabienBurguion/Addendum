package org.epita.models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ResourceType {
    public static final int ORE = 0;
    public static final int CLAY = 1;
    public static final int OBSIDIAN = 2;
    public static final int GEODE = 3;
    public static final int DIAMOND = 4;

    public static int getLastResourceType() {
        try {
            Field[] fields = ResourceType.class.getDeclaredFields();
            List<Integer> values = new ArrayList<>();

            for (Field field : fields) {
                if (field.getType() == int.class && java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    values.add((int) field.get(null));
                }
            }

            return values.stream().max(Integer::compare).orElse(0);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get last resource type", e);
        }
    }
}
