package sorting;

import car.Car;
import util.CustomArrayList;

public class CarSorterStrategy {  //класс-контекст, хранит strategy и применяет её к списку машин CustomArrayList<Car>

    private CarSortStrategy strategy;

    public CarSorterStrategy() { // пустой конструктор для создания сортировщика, без стратегии
    }

    public CarSorterStrategy(CarSortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(CarSortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(CustomArrayList<Car> cars) {
        if (strategy == null) {
            throw new IllegalStateException("Стратегия сортировки не установлена");
        }
        strategy.sort(cars);
    }

}
