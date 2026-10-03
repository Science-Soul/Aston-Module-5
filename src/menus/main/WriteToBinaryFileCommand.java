package menus.main;

import car.Car;
import io.File;
import menus.Menu;
import util.CustomArrayList;
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

        System.out.printf("Введите имя файла (%d - отмена): ", menu.CODE_EXIT);
        String fileName = menu.getStringAnswer(scanner);
        if (fileName.equals(String.valueOf(menu.CODE_EXIT))) return;
        String path = "resources/data/" + fileName;
        CustomArrayList<Car> list = File.read(CustomArrayList.class, path).orElse(new CustomArrayList<>());
        list.addAll(Menu.cars);
        File.write(path, list);
        System.out.print("Файл сохранен в папке resources/data");
    }

    @Override
    public String getDescription() {
        return "Записать коллекцию в бинарный файл";
    }
}
