package menus.main;

import menus.Menu;
import sorting.CarSorterStrategy;
import sorting.SortType;

public class SortByBrandCommand implements MenuCommand {

    @Override
    public void execute() {
        if (Menu.cars.isEmpty()) {
            System.out.println("Массив пуст! Сначала заполните массив.");
            return;
        }

        CarSorterStrategy sorter = new CarSorterStrategy();
        sorter.setStrategy(SortType.BY_BRAND.createStrategy());
        sorter.sort(Menu.cars);
        System.out.println(">>> Сортировка по бренду <<<");
        System.out.println(Menu.cars);
    }

    @Override
    public String getDescription() {
        return "Сортировать по бренду";
    }
}