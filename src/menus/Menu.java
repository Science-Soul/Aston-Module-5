package menus;

import car.Car;
import util.CustomArrayList;

import java.util.List;
import java.util.Scanner;

public abstract class Menu {

    final int CODE_EXIT = 0;
    static List<Car> cars = new CustomArrayList<>();

    abstract void start(Scanner sc, String ... args);


    void printWarn(String msg) {
        System.out.println(msg);
    }

    void printEnter(){
        System.out.println("Ввод: ");
    }

    String getStringAnswer(Scanner sc) {
        return sc.nextLine();
    }

    int getIntAnswer(Scanner sc) {
        boolean error;
        int result = 0;
        do {
            try {
                error = false;
                result = sc.nextInt();
            } catch (Exception ignored) {
                error = true;
                printWarn("Это не число!");
                printEnter();
            }
        } while (error);

        return result;
    }

    int getIntAnswer(Scanner sc, int leftBorder, int rightBorder, int exit) {
        boolean inBorders;
        int result;
        do {
            result = getIntAnswer(sc);
            inBorders = result > leftBorder && result < rightBorder;
            if (result == exit) break;
            if (!inBorders) {
                printWarn("Необходимо число от %s до %s!".formatted(leftBorder, rightBorder));
                printEnter();
            }
        } while (!inBorders);

        return result;
    }
}
