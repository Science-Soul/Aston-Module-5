package sorting;

import car.Car;
import util.CustomArrayList;

// отдльная сортировка по полю year класса Car,чётные значения встают в натуральном порядке,
// а нечётные остаютмя на своих местах.
// алгоритм вставками - запоминаем все четные элементы и их позиции, сортируем только эти элементы, а далее
// складываем отсортированные чётные значения обратно на те же позиции, нечётные не трогаем.
public class SortByYearEvenOnly implements CarSortStrategy { //

    @Override
    public void sort(CustomArrayList<Car> cars) {
        CustomArrayList<Integer> evenPositions = new CustomArrayList<>();
        CustomArrayList<Car> evenValues = new CustomArrayList<>();

        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            if (car.getYear() % 2 == 0) {
                evenPositions.add(i);
                evenValues.add(car);
            }
        }

        SortAlgo.insertionSort(evenValues, (a, b) -> Integer.compare(a.getYear(), b.getYear()));

        for (int i = 0; i < evenPositions.size(); i++) {
            cars.set(evenPositions.get(i), evenValues.get(i));
        }
    }

}
