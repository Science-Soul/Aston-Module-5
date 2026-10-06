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
        int code_exit = -1;
        System.out.printf("Введите индекс элемента, копии которого хотите сосчитать (%d - отмена): ", code_exit);
        int index = menu.getIntAnswer(scanner, 0, list.size() - 1, code_exit);
        if (index == code_exit) return;
        long numberOfElements = MultiThreadCounter.countN(list, index);
        System.out.println("Число вхождений элемента " + list.get(index) + " = " + numberOfElements);
    }

    @Override
    public String getDescription() {
        return "Посчитать число вхождений элемента с индексом N";
    }
}