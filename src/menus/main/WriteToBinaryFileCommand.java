package menus.main;

import io.File;
import menus.Menu;
import util.JsonFileLogger;

import java.util.Scanner;

public class WriteToBinaryFileCommand implements MenuCommand {
    Scanner scanner;
    Menu menu;

    public WriteToBinaryFileCommand(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    @Override
    public void execute() {
        if (Menu.cars.isEmpty()) {
            System.out.println("Массив пуст! Сначала заполните массив.");
            return;
        }

        System.out.print("Введите имя файла: ");
        String fileName = menu.getStringAnswer(scanner);
        String path = "resources/data/" + fileName;
        File.write(path, Menu.cars);
        System.out.print("Файл сохранен в папке resources/data");
    }

    @Override
    public String getDescription() {
        return "Записать коллекцию в бинарный файл";
    }
}
