package sorting;

public enum SortType { // enum фабрика стратегий ОПЦИОНАЛЬНО, (для выбора пунктами меню).

    BY_BRAND,
    BY_MODEL,
    BY_YEAR,
    BY_YEAR_EVEN_ONLY;

    public CarSortStrategy createStrategy() {
        return switch (this) {
            case BY_BRAND -> new SortByBrand();
            case BY_MODEL -> new SortByModel();
            case BY_YEAR -> new SortByYear();
            case BY_YEAR_EVEN_ONLY -> new SortByYearEvenOnly();
        };
    }


}
