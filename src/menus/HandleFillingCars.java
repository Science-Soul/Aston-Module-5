package menus;

import car.Car;
import java.util.Scanner;
import java.util.stream.IntStream;

public class HandleFillingCars extends Menu {
    private static final int NEED_VALUES = 3;

    /**
     * Подменю ручного заполнения массива автомобилей
     * @param args принимается одно значение: длина массива
     */
    @Override
    public void start(Scanner sc, String ... args) {
        int length = parseArgs(args);
        System.out.printf("""
                
                =======Ручное заполнение массива=======
                
                Далее вводите данные авто через запятую (бренд, модель, год) %s раз(а) (%d - закончить):
                """, length, CODE_EXIT);
        startReadCars(sc, length);
        System.out.println("\nЗаполнение окончено.");
        System.out.println(cars);
    }

    int parseArgs(String[] args){
        if (args.length < 1) {
            throw new IllegalArgumentException("Отсутствует длина массива");
        }
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Передано не число для длины массива");
        }
    }

    private void startReadCars(Scanner sc, int length){
        cars.clear();
        for (int i = 0; i < length; i++) {
            Car.Builder builder = Car.builder();
            System.out.printf("%s. ", i+1);
            String[] answer = getStringAnswer(sc).split(",");

            if (answer.length != 0 && answer[0].equals(String.valueOf(CODE_EXIT))) // добавил проверку
                // длины, потому что при вводе ,,, кидал ошибку
                break;
            if (answer.length < NEED_VALUES)  {
                printWarn("Вы ввели неполные данные!");
                i--;
                continue;
            }


            boolean isOk = IntStream.range(0, NEED_VALUES)
                    .allMatch(k -> buildCar(builder, answer[k].strip(), k + 1));
            if (!isOk) {
                i--;
                continue;
            }
            cars.add(builder.build());
        }
    }

    boolean buildCar(Car.Builder builder, String value, int order){
        if (value.isBlank()) {
            printWarn("Вы ввели пустое значение!");
            return false;
        }
        int year = -1;
        try {
            year = Integer.parseInt(value);
            if (!verifyYear(year, 1980, 2026))
                return false;
        } catch (NumberFormatException ignored){
            if (order == NEED_VALUES) {
                printWarn("Неверный формат года!");
                return false;
            }
        }
        if (order < NEED_VALUES && year != -1) {
            printWarn("Бренд или модель не может быть числом!");
            return false;
        }

        if (order == 1)
            builder.brand(value);
        else if (order == 2)
            builder.model(value);
        else
            builder.year(year);
        return true;
    }

    boolean verifyYear(int year, int leftBorder, int rightBorder) {
        if (year < leftBorder || year > rightBorder) {
            printWarn("Год должен быть с %s по %s!".formatted(leftBorder, rightBorder));
            return false;
        }
        return true;
    }
}