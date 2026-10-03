package menus.main;

import menus.Menu;
import sorting.CarSorterStrategy;
import sorting.SortType;

public class SortByEvenYearCommand implements MenuCommand {

    @Override
    public void execute() {
        if (Menu.cars.isEmpty()) {
            System.out.println("Массив пуст! Сначала заполните массив.");
            return;
        }

        CarSorterStrategy sorter = new CarSorterStrategy();
        sorter.setStrategy(SortType.BY_YEAR_EVEN_ONLY.createStrategy());
        sorter.sort(Menu.cars);
        System.out.println(">>> Сортировка только четных годов <<<");
        System.out.println(Menu.cars);
    }

    @Override
    public String getDescription() {
        return "Сортировать по четным годам";
    }
}
