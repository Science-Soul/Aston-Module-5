package menus.main;

import car.Car;
import generators.CarsGenerator;
import generators.ObjectsGenerator;
import menus.Menu;

import java.util.List;
import java.util.Scanner;

public class FillRandomCommand implements MenuCommand {
    Scanner scanner;
    Menu menu;
    ObjectsGenerator<Car> generator = new CarsGenerator();

    public FillRandomCommand(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    @Override
    public void execute() {
        System.out.print("Введите количество машин: ");
        int length = menu.getIntAnswer(scanner, 1, 2000000, 0);
        Menu.cars.clear();
        generator.generate(Menu.cars, length);
        System.out.println(Menu.cars);
    }

    @Override
    public String getDescription() {
        return "Заполнить случайными объектами";
    }
}
