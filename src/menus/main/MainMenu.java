package menus.main;

import menus.Menu;
import java.util.Scanner;

public class MainMenu extends Menu {
    private final Scanner scanner = new Scanner(System.in);
    private boolean isRunning = true;

    MenuCommand[] commands = new MenuCommand[]{
        new FillRandomCommand(scanner, this),
        new FillManuallyCommand(scanner, this),
        new FillFromFileCommand(scanner, this),
        new SortByBrandCommand(),
        new SortByModelCommand(),
        new SortByYearCommand(),
        new SortByEvenYearCommand(),
        new WriteToJSONCommand(scanner, this),
        new WriteToBinaryFileCommand(scanner, this),
        new CountCommand(scanner, this),
    };

    @Override
    public void start(Scanner sc, String... args) {
        while (isRunning) {
            printMenu();
            int choice = getIntAnswer(this.scanner, 1, commands.length, CODE_EXIT);
            if (choice == CODE_EXIT) return;
            System.out.println();
            commands[choice - 1].execute();
        }
    }

    public void printMenu() {
        System.out.println("\n==== ГЛАВНОЕ МЕНЮ ====");

        for (int i = 0; i < commands.length; i++) {
            System.out.printf("%d. %s\n", i + 1, commands[i].getDescription());
        }

        System.out.printf("\n%d. Выход%n", CODE_EXIT);
        System.out.print("\nВыберите пункт меню: ");
    }
}