import car.Car;
import test.Test;

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
        Test.testFile();
    }
}