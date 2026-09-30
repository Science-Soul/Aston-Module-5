package sorting;

import car.Car;
import util.CustomArrayList;

public class SortByBrand implements CarSortStrategy {  // класс компаратор, сортировка по бренду вставками.

    private int compare(Car a, Car b) {
        return a.getBrand().compareToIgnoreCase(b.getBrand());
    }

    @Override
    public void sort(CustomArrayList<Car> cars) { // сортировка вставками
        for (int i = 1; i < cars.size(); i++) {
            Car current = cars.get(i);
            int j = i - 1;
            while (j >= 0 && compare(cars.get(j), current) > 0) {
                cars.set(j + 1, cars.get(j));
                j--;
            }
            cars.set(j + 1, current);
        }
    }

}
