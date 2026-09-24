import car.Car;
import util.CustomArrayList;

import java.util.ArrayList;
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

        List<Car> example = List.of(toyota, renault);
        List<Car> cust = new CustomArrayList<>(example);
        List<Car> def = new ArrayList<>(example);
        System.out.println(def);
        System.out.println(cust);
        System.out.println(def.equals(cust));
        for (Car car : cust) {
            System.out.println(car);
        }
    }
}