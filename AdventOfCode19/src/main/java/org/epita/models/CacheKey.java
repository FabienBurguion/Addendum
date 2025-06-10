package org.epita.models;

import java.util.Arrays;
import java.util.Objects;

public record CacheKey(int time, int[] bots, int[] resources) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheKey other)) return false;
        return time == other.time &&
                Arrays.equals(bots, other.bots) &&
                Arrays.equals(resources, other.resources);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(time);
        result = 31 * result + Arrays.hashCode(bots);
        result = 31 * result + Arrays.hashCode(resources);
        return result;
    }
}
