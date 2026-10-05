package menus.main;

import menus.Menu;
import sorting.CarSortStrategy;
import sorting.CarSorterStrategy;

public abstract class SortCommand implements MenuCommand {
    protected CarSortStrategy strategy;
    protected String title;
    protected String description;
    @Override
    public void execute() {
        if (Menu.cars.isEmpty()) {
            System.out.println("Массив пуст! Сначала заполните массив.");
            return;
        }

        CarSorterStrategy sorter = new CarSorterStrategy();
        sorter.setStrategy(strategy);
        sorter.sort(Menu.cars);
        System.out.println(title);
        System.out.println(Menu.cars);
    }

    @Override
    public String getDescription() {
        return description;
    }
}