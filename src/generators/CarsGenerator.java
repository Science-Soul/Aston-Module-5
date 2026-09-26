package generators;

import car.Car;
import util.CustomArrayList;

import java.util.List;
import java.util.Random;

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
    public List<Car> generate(int length) {
        List<Car> res = new CustomArrayList<>();
        Car car;
        for (int i = 0; i < length; i++) {
            int rangeDate = random.nextInt(-11, 27);
            int year = 2000 + rangeDate;
            car = Car.builder()
                    .brand(pick(brands)).model(pick(models))
                    .year(year).build();
            res.add(car);
        }
        return res;
    }

    private String pick(List<String> values){
        return values.get(random.nextInt(values.size()));
    }
}
