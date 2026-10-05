package menus.main;

import menus.Menu;
import sorting.CarSorterStrategy;
import sorting.SortType;

public class SortByYearCommand extends SortCommand {
    public SortByYearCommand() {
        strategy = SortType.BY_YEAR.createStrategy();
        title = ">>> Сортировка по году выпуска <<<";
        description = "Сортировать по году выпуска";
    }
}
