package menus.main;

import menus.Menu;
import sorting.CarSorterStrategy;
import sorting.SortType;

public class SortByEvenYearCommand extends SortCommand {
    public SortByEvenYearCommand() {
        strategy = SortType.BY_YEAR_EVEN_ONLY.createStrategy();
        title = ">>> Сортировка только четных годов <<<";
        description = "Сортировать по четным годам";
    }
}
