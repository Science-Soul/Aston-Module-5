package generators;

import car.Car;
import util.CustomArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class CarsGenerator implements ObjectsGenerator<Car> {
    private List<String> brands;
    private List<String> models;
    private Random random;

    public CarsGenerator() {
        models = new CustomArrayList<>(List.of(
                "Logan", "Corolla", "Camry", "Civic",
                "Accord", "Focus", "Mondeo", "Octavia",
                "Superb", "Passat"
        ));
        brands = new CustomArrayList<>(List.of(
                "Renault", "Toyota", "Honda", "Ford",
                "Skoda", "Volkswagen", "BMW", "Mercedes-Benz",
                "Audi", "Kia"
        ));
        random = new Random();
    }

    @Override
    public void generate(List<Car> targetList, int length) {
        // Генерируем объекты напрямую в предоставленный список
        Stream.generate(() -> Car.builder()
                .brand(pick(brands))
                .model(pick(models))
                .year(pickYear(1980, 2026))
                .build())
            .limit(length)
            .forEach(targetList::add); // Просто добавляем элементы в наш список
    }

    private String pick(List<String> values){
        return values.get(random.nextInt(values.size()));
    }

    private int pickYear(int minYear, int maxYear){
        return random.nextInt(minYear, maxYear+1);
    }
}