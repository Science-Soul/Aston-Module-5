package menus.main;

import menus.HandleFillingCars;
import menus.Menu;
import java.util.Scanner;

public class FillManuallyCommand implements MenuCommand {
    Scanner scanner;
    Menu menu;

    public FillManuallyCommand(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    @Override
    public void execute() {
        System.out.printf("Введите количество машин (%d - отмена): ", menu.CODE_EXIT);
        int choice = menu.getIntAnswer(scanner);
        if (choice == menu.CODE_EXIT) return;
        String s = String.valueOf(choice);
        HandleFillingCars hfc = new HandleFillingCars();
        hfc.start(scanner, s);
    }

    @Override
    public String getDescription() {
        return "Заполнить вручную";
    }
}