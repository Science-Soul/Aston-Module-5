package sorting;

import car.Car;
import util.CustomArrayList;

public class SortByYear implements CarSortStrategy { // // класс компаратор,  сортировка по году вставками.

    private int compare(Car a, Car b) {
        return Integer.compare(a.getYear(), b.getYear());
    }

    @Override
    public void sort(CustomArrayList<Car> cars) {
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
