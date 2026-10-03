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
        if (list.isEmpty()) {
            System.out.println("Массив пуст! Сначала заполните массив.");
            return;
        }
        System.out.println("Размер массива = " + list.size());
        System.out.printf("Введите индекс элемента, копии которого хотите сосчитать (%d - отмена): ", menu.CODE_EXIT);
        int index = menu.getIntAnswer(scanner, 0, list.size() - 1, 0);
        if (index == menu.CODE_EXIT) return;
        long numberOfElements = MultiThreadCounter.countN(list, index);
        System.out.println("Число вхождений элемента " + list.get(index) + " = " + numberOfElements);
    }

    @Override
    public String getDescription() {
        return "Посчитать число вхождений элемента с индексом N";
    }
}