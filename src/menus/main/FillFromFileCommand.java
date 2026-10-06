package menus.main;

import car.Car;
import io.File;
import menus.Menu;
import util.CustomArrayList;
import java.util.Scanner;

public class FillFromFileCommand implements MenuCommand {
    Scanner scanner;
    Menu menu;

    public FillFromFileCommand(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    @Override
    public void execute() {
        System.out.printf("Введите путь к файлу (%d - отмена): ", menu.CODE_EXIT);
        String path = menu.getStringAnswer(scanner);
        if (path.equals(String.valueOf(menu.CODE_EXIT))) return;
        CustomArrayList<Car> list = File.read(CustomArrayList.class, path).orElse(null);
        if (list != null) {
            System.out.println(list);
        }
        Menu.cars = list;
    }

    @Override
    public String getDescription() {
        return "Заполнить из файла";
    }
}
