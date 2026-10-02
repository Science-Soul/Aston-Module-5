package util;

import java.util.List;
import java.util.Objects;

public class MultiThreadCounter {
    public static <T> long countN(List<T> list, int indexOfN) {
        return list.parallelStream()
            .filter(element -> Objects.equals(element, list.get(indexOfN)))
            .count();
    }
}
