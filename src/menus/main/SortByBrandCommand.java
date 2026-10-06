package menus.main;

import sorting.SortType;

public class SortByBrandCommand extends SortCommand {
    public SortByBrandCommand() {
        strategy = SortType.BY_BRAND.createStrategy();
        title = ">>> Сортировка по бренду <<<";
        description = "Сортировать по бренду";
    }
}