package menus;

import car.Car;
import util.CustomArrayList;

import java.util.List;
import java.util.Scanner;

public abstract class Menu {

    final int CODE_EXIT = 0;
    public static List<Car> cars = new CustomArrayList<>();

    abstract public void start(Scanner sc, String ... args);


    public void printWarn(String msg) {
        System.out.println(msg);
    }

    public void printEnter(){
        System.out.println("Ввод: ");
    }

    public String getStringAnswer(Scanner sc) {
        return sc.nextLine();
    }

    public int getIntAnswer(Scanner sc) {
        boolean error;
        int result = 0;
        do {
            try {
                error = false;
                String input = sc.nextLine().trim(); // если делать result = sc.nextInt(), то при использовании
                // в команде возникает баг - непреднамеренный ввод пустой строки
                result = Integer.parseInt(input);
            } catch (Exception ignored) {
                error = true;
                printWarn("Это не число!");
                printEnter();
            }
        } while (error);

        return result;
    }

    public int getIntAnswer(Scanner sc, int leftBorder, int rightBorder, int exit) {
        boolean inBorders;
        int result;
        do {
            result = getIntAnswer(sc);
            inBorders = result >= leftBorder && result <= rightBorder;
            if (result == exit) break;
            if (!inBorders) {
                printWarn("Необходимо число от %s до %s!".formatted(leftBorder, rightBorder));
                printEnter();
            }
        } while (!inBorders);

        return result;
    }
}
