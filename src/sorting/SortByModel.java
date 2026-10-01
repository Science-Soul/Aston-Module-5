package sorting;

import car.Car;
import util.CustomArrayList;

public class SortByModel implements CarSortStrategy { // класс компаратор, сортировка по модели вставками.

    public void sort(CustomArrayList<Car> cars) {
        SortAlgo.insertionSort(cars, (a, b) -> a.getModel().compareToIgnoreCase(b.getModel()));
    }

}
