package menus.main;

import sorting.SortType;

public class SortByModelCommand extends SortCommand {
    public SortByModelCommand() {
        strategy = SortType.BY_MODEL.createStrategy();
        title = ">>> Сортировка по модели <<<";
        description = "Сортировать по модели";
    }
}