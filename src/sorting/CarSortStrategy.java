package sorting;

import car.Car;
import util.CustomArrayList;

public interface CarSortStrategy { // общий интерфейс паттерна стратегия для всех сортировок.

    void sort(CustomArrayList<Car> cars);
}
