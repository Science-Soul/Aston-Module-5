import car.Car;
import generators.CarsGenerator;
import menus.Menu;
import menus.main.MainMenu;
import util.CustomArrayList;
import util.TestMultithreds;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CarsGenerator g = new CarsGenerator();
        List<Car> list = new CustomArrayList<>();
        g.generate(list, 100000);

        TestMultithreds.test(list, Car.builder().year(2000).build());
        Scanner scanner = new Scanner(System.in);
        Menu mainMenu = new MainMenu();
        mainMenu.start(scanner);
    }
}