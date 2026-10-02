import menus.Menu;
import menus.main.MainMenu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Menu mainMenu = new MainMenu();
        mainMenu.start(scanner);
    }
}