package sorting;

import car.Car;
import util.CustomArrayList;

public class SortByBrand implements CarSortStrategy {  // класс компаратор, сортировка по бренду вставками.

    public void sort(CustomArrayList<Car> cars) {
        SortAlgo.insertionSort(cars, (a, b) -> a.getBrand().compareToIgnoreCase(b.getBrand()));
    }

}
