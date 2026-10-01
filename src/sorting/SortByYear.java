package sorting;

import car.Car;
import util.CustomArrayList;

public class SortByYear implements CarSortStrategy { // // класс компаратор,  сортировка по году вставками.

    public void sort(CustomArrayList<Car> cars) {
        SortAlgo.insertionSort(cars, (a, b) -> Integer.compare(a.getYear(), b.getYear()));
    }

}
