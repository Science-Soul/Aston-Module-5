import car.Car;
import test.Test;
import util.CustomArrayList;
import util.JsonFileLogger;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Car toyota = Car.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2023)
                .build();

        System.out.println(toyota);

        Car renault = Car.builder()
                .brand("Renault")
                .model("Logan")
                .year(2024)
                .build();

        System.out.println(renault);

        Test.testCustomArrayList();

        // Дополнительное задание 2: запись отсортированной коллекции машин
        // (созданных через билдер) и результата поиска одной из них в JSON-файл.
        Car bmw = Car.builder().brand("BMW").model("X5").year(2021).build();
        Car kia = Car.builder().brand("Kia").model("Rio").year(2022).build();

        List<Car> cars = new CustomArrayList<>();
        cars.add(toyota);
        cars.add(renault);
        cars.add(bmw);
        cars.add(kia);

        cars.sort(Comparator.comparingInt(Car::getYear));
        System.out.println(cars);
        JsonFileLogger.logSortedCollection(cars, "sorted_cars.json");

        Car searched = kia;
        int index = cars.indexOf(searched);
        JsonFileLogger.logFoundValue(
                searched.getBrand() + " " + searched.getModel(),
                index,
                "found_cars.json"
        );
    }
}