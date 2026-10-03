import car.Car;
import io.File;
import util.CustomArrayList;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        // Хочу создать файл, из которого будем потом заполнять коллекцию
        CustomArrayList<Car> sample = new CustomArrayList<Car>(List.of(
            Car.builder().brand("Toyota").model("Corolla").year(2020).build(),
            Car.builder().brand("BMW").model("X5").year(2024).build(),
            Car.builder().brand("Renault").model("Logan").year(2015).build(),
            Car.builder().brand("Toyota").model("Corolla").year(2020).build()
        ));

        String fileName = "resources/data/demoList.txt";
        File.write(fileName, sample);
        var list = File.read(CustomArrayList.class, fileName).orElse(null);
        System.out.println(list);
    }
}
