package sorting;

import car.Car;
import util.CustomArrayList;

// отдльная сортировка по полю year класса Car,чётные значения встают в натуральном порядке,
// а нечётные остаютмя на своих местах.
// алгоритм вставками - запоминаем все четные элементы и их позиции, сортируем только эти элементы, а далее
// складываем отсортированные чётные значения обратно на те же позиции, нечётные не трогаем.
public class SortByYearEvenOnly implements CarSortStrategy { //

    private void sortInsertion(CustomArrayList<Car> list) {
        for (int i = 1; i < list.size(); i++) {
            Car current = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).getYear() > current.getYear()) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, current);
        }
    }

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

        sortInsertion(evenValues);

        for (int i = 0; i < evenPositions.size(); i++) {
            cars.set(evenPositions.get(i), evenValues.get(i));
        }
    }

}
