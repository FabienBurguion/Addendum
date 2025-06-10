package org.epita.models;

import java.util.Arrays;
import java.util.Objects;

public record CacheKey(int time, int[] bots, int[] resources) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheKey(int time1, int[] bots1, int[] resources1))) return false;
        return time == time1 &&
                Arrays.equals(bots, bots1) &&
                Arrays.equals(resources, resources1);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(time);
        result = 31 * result + Arrays.hashCode(bots);
        result = 31 * result + Arrays.hashCode(resources);
        return result;
    }
}
