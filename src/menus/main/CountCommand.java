package menus.main;

import menus.Menu;
import util.MultiThreadCounter;
import java.util.Scanner;

public class CountCommand implements MenuCommand {
    Scanner scanner;
    Menu menu;

    public CountCommand(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    @Override
    public void execute() {
        var list = Menu.cars;
        System.out.println("Размер массива = " + list.size());
        if (list.isEmpty()) {
            System.out.println("Массив пуст! Сначала заполните массив.");
        } else {
            System.out.println("Введите индекс элемента, копии которого хотите сосчитать: ");
            int index = menu.getIntAnswer(scanner, 0, list.size() - 1, 0);
            long numberOfElements = MultiThreadCounter.countN(list, index);
            System.out.println("Число вхождений элемента " + list.get(index) + " = " + numberOfElements);
        }
    }

    @Override
    public String getDescription() {
        return "Посчитать число вхождений элемента с индексом N";
    }
}