package test;

import car.Car;
import sorting.*;
import util.CustomArrayList;

import java.util.List;

public class SortTest {

    public static void testSortByBrand() {
        CustomArrayList<Car> cars = sample();
        CarSorterStrategy sorter = new CarSorterStrategy(new SortByBrand());
        sorter.sort(cars);

        List<String> expected = List.of("Audi", "BMW", "Kia", "Renault", "Toyota");
        for (int i = 0; i < cars.size(); i++) {
            Test.assertion(cars.get(i).getBrand().equals(expected.get(i)));
        }
    }

    public static void testSortByModel() {
        CustomArrayList<Car> cars = sample();

        System.out.println(">ДО сортировки<");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(i + ": " + cars.get(i).getBrand() + " / " + cars.get(i).getModel());
        }

        CarSorterStrategy sorter = new CarSorterStrategy(new SortByModel());
        sorter.sort(cars);

        System.out.println(">ПОСЛЕ сортировки<");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(i + ": " + cars.get(i).getBrand() + " / " + cars.get(i).getModel());
        }

        List<String> expected = List.of("A4", "Corolla", "Logan", "Rio", "X5");
        for (int i = 0; i < cars.size(); i++) {
            Test.assertion(cars.get(i).getModel().equals(expected.get(i)));
        }
    }

    public static void testSortByYear() {
        CustomArrayList<Car> cars = sample();
        CarSorterStrategy sorter = new CarSorterStrategy(new SortByYear());
        sorter.sort(cars);

        int[] expected = {2015, 2018, 2020, 2022, 2024};
        for (int i = 0; i < cars.size(); i++) {
            Test.assertion(cars.get(i).getYear() == expected[i]);
        }
    }

    public static void testSortByYearEvenOnly() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(Car.builder().brand("A").model("M").year(5).build());  // нечетный, стоит на своем месте
        cars.add(Car.builder().brand("B").model("M").year(4).build());  // четный
        cars.add(Car.builder().brand("C").model("M").year(7).build());  // нечнтный, стоит на своем месте
        cars.add(Car.builder().brand("D").model("M").year(2).build());  // четный
        cars.add(Car.builder().brand("E").model("M").year(3).build());  // нечетный, стоит на своем месте
        cars.add(Car.builder().brand("F").model("M").year(6).build());  // четный

        CarSorterStrategy sorter = new CarSorterStrategy(new SortByYearEvenOnly());
        sorter.sort(cars);

        // Нечётные остаются на местах 0, 2, 4
        Test.assertion(cars.get(0).getYear() == 5);
        Test.assertion(cars.get(2).getYear() == 7);
        Test.assertion(cars.get(4).getYear() == 3);

        // Чётные на местах 1, 3, 5 -> отсортированы: 2, 4, 6
        Test.assertion(cars.get(1).getYear() == 2);
        Test.assertion(cars.get(3).getYear() == 4);
        Test.assertion(cars.get(5).getYear() == 6);
    }

    public static void testSortTypeEnum() {
        Test.assertion(SortType.BY_BRAND.createStrategy() instanceof SortByBrand);
        Test.assertion(SortType.BY_MODEL.createStrategy() instanceof SortByModel);
        Test.assertion(SortType.BY_YEAR.createStrategy() instanceof SortByYear);
        Test.assertion(SortType.BY_YEAR_EVEN_ONLY.createStrategy() instanceof SortByYearEvenOnly);
    }

    private static CustomArrayList<Car> sample() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(Car.builder().brand("Toyota").model("Corolla").year(2020).build());
        cars.add(Car.builder().brand("BMW").model("X5").year(2024).build());
        cars.add(Car.builder().brand("Renault").model("Logan").year(2015).build());
        cars.add(Car.builder().brand("Audi").model("A4").year(2018).build());
        cars.add(Car.builder().brand("Kia").model("Rio").year(2022).build());
        return cars;
    }


}
