package sorting;

import util.CustomArrayList;

import java.util.Comparator;

public class SortAlgo {

    private SortAlgo() {}

    // метод принимает любой кастомный лист (список). сортировка вставками.
    public static <T> void insertionSort(CustomArrayList<T> list, Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            T current = list.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(list.get(j), current) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, current);
        }
    }

}

