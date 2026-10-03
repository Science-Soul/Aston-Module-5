import car.Car;
import sorting.*;
import util.CustomArrayList;

public class SortDemo {                          // класс для проверки корректности сортировок. УДАЛИТЬ!
    public static void main(String[] args) {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(Car.builder().brand("Toyota").model("Corolla").year(2020).build());
        cars.add(Car.builder().brand("BMW").model("X5").year(2024).build());
        cars.add(Car.builder().brand("Renault").model("Logan").year(2015).build());
        cars.add(Car.builder().brand("Audi").model("A4").year(2018).build());
        cars.add(Car.builder().brand("Kia").model("Rio").year(2022).build());

        CarSorterStrategy sorter = new CarSorterStrategy();

        System.out.println(">>> Исходная сортировка <<<");
        print(cars);

        sorter.setStrategy(SortType.BY_BRAND.createStrategy());
        sorter.sort(cars);
        System.out.println(">>> Сортировка по бренду <<<");
        print(cars);

        sorter.setStrategy(SortType.BY_MODEL.createStrategy());
        sorter.sort(cars);
        System.out.println(">>> Сортировка по модели <<<");
        print(cars);

        sorter.setStrategy(SortType.BY_YEAR.createStrategy());
        sorter.sort(cars);
        System.out.println(">>> Сортировка по году <<<");
        print(cars);
    }

    private static void print(CustomArrayList<Car> cars) {
        for (Car c : cars) System.out.println(c);
        System.out.println();
    }
}