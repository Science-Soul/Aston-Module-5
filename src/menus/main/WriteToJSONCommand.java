package menus.main;

import menus.Menu;
import util.JsonFileLogger;
import java.util.Scanner;

public class WriteToJSONCommand implements MenuCommand {
    Scanner scanner;
    Menu menu;

    public WriteToJSONCommand(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    @Override
    public void execute() {
        if (Menu.cars.isEmpty()) {
            System.out.println("Массив пуст! Сначала заполните массив.");
            return;
        }

        System.out.printf("Введите имя файла (%d - отмена): ", menu.CODE_EXIT);
        String fileName = menu.getStringAnswer(scanner);
        if (fileName.equals(String.valueOf(menu.CODE_EXIT))) return;
        JsonFileLogger.logSortedCollection(Menu.cars, fileName);
        System.out.print("Файл сохранен в папке resources/data");
    }

    @Override
    public String getDescription() {
        return "Записать коллекцию в файл JSON";
    }
}