package test;

import car.Car;
import util.CustomArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import static test.Test.assertion;
import static test.Test.assertionThrow;
import static util.MultiThreadCounter.countN;

public class MultiCounterTest {
    private static CustomArrayList<Car> sample = new CustomArrayList<Car>(List.of(
        Car.builder().brand("Toyota").model("Corolla").year(2020).build(),
        Car.builder().brand("BMW").model("X5").year(2024).build(),
        Car.builder().brand("Renault").model("Logan").year(2015).build(),
        Car.builder().brand("Toyota").model("Corolla").year(2020).build()
    ));

    public static void runAllTests() {
        System.out.println("Запуск юнит-тестов для метода многопоточного подсчета...");

        testSuccessCount();
        testCountWithNullElements();
        testCountSingleElement();
        testConcurrentModification();

        System.out.println("Все тесты многопоточного подсчета успешно пройдены!");
    }

    // Тест 1: Базовый успешный сценарий подсчета строк
    private static void testSuccessCount() {
        // Ищем brand("Toyota").model("Corolla").year(2020) по индексу 0. Ожидаем 2 вхождения.
        long result = countN(sample, 0);
        assertion(result == 2);

        // Ищем brand("BMW").model("X5").year(2024) по индексу 1. Ожидаем 1 вхождение.
        long result2 = countN(sample, 1);
        assertion(result2 == 1);

        System.out.println("  - testSuccessCount: Пройден");
    }

    // Тест 2: Подсчет элементов в списке, содержащем null
    private static void testCountWithNullElements() {
        sample.set(1, null);
        sample.set(3, null);

        // Ищем null по индексу 1. Ожидаем 2 вхождения.
        long result = countN(sample, 1);
        assertion(result == 2);

        System.out.println("  - testCountWithNullElements: Пройден");
    }

    // Тест 3: Список из одного элемента
    private static void testCountSingleElement() {
        CustomArrayList<Car> list = new CustomArrayList<>();
        list.add(Car.builder().brand("Toyota").model("Corolla").year(2020).build());

        long result = countN(list, 0);
        assertion(result == 1);

        System.out.println("  - testCountSingleElement: Пройден");
    }

    // Тест 4: Проверка защиты от изменения списка во время параллельного подсчета
    private static void testConcurrentModification() {
        CustomArrayList<Car> list = new CustomArrayList<>();
        for (int i = 0; i < 300_000; i++) {
            list.add(Car.builder().brand("Toyota").model("Corolla").year(2020).build());
        }

        assertionThrow(ConcurrentModificationException.class, () -> {
            // Мешающий поток
            Thread modifierThread = new Thread(() -> {
                try {
                    Thread.sleep(15);
                    list.add(Car.builder().brand("Toyota").model("Corolla").year(2020).build());
                } catch (InterruptedException ignored) {
                }
            });

            modifierThread.start();

            try {
                countN(list, 0);
            } finally {
                try {
                    modifierThread.join();
                } catch (Exception ignored) {
                }
            }
        });

        System.out.println("  - testConcurrentModification: Пройден");
    }
}
