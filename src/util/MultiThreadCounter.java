package util;

import java.util.List;
import java.util.Objects;

public class MultiThreadCounter {
    public static <T> long countN(List<T> list, int indexOfN) {
        if (list == null || indexOfN < 0 || indexOfN >= list.size()) {
            System.out.println("Некорректные данные!");
            return 0;
        }

        T targetElement = list.get(indexOfN);
        return list.parallelStream()
            .filter(element -> Objects.equals(element, targetElement))
            .count();
    }
}