package test;

import car.Car;
import io.File;
import util.CustomArrayList;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class Test {
    @SuppressWarnings({"CollectionAddedToSelf", "ResultOfMethodCallIgnored"})
    public static void testCustomArrayList(){
        new ArrayList<>();
        List<Integer> cal = new CustomArrayList<>();
        assertion(cal.isEmpty());
        cal.add(3);
        cal.add(7);
        cal.add(1);
        assertion(cal.size() == 3);
        assertion(cal.get(1) == 7);
        assertion(cal.contains(7));
        assertion(cal.remove((Object)7));
        assertion(cal.size() == 2);
        assertion(cal.get(1) == 1);
        assertion(!cal.remove((Object)1123));
        assertion(cal.remove(0) == 3);
        assertion(cal.get(0) == 1);
        List<Integer> finalCal = cal;
        assertionThrow(IndexOutOfBoundsException.class, () -> finalCal.get(10));
        assertionThrow(IndexOutOfBoundsException.class, () -> finalCal.remove(10));
        assertion(cal.addAll(List.of(5,4,3,2,1)));
        // Элементы на данный момент: 1,5,4,3,2,1
        assertion(cal.size() == 6);
        assertion(cal.get(1) == 5 && cal.get(cal.size()-1) == 1);
        assertion(cal.indexOf(3) == 3);
        assertion(cal.set(4, 10) == 2);
        assertion(cal.addAll(1,List.of(6,6,6)));
        assertion(Arrays.equals(cal.toArray(), new Object[]{1, 6, 6, 6, 5, 4, 3, 10, 1}));
        for (Integer i : cal) {
            assertion(cal.contains(i));
        }
        assertion(Arrays.equals(
                cal.toArray(new Integer[3]),
                new Integer[]{1, 6, 6, 6, 5, 4, 3, 10, 1}));
        assertion(Arrays.equals(
                cal.toArray(new Integer[11]),
                new Integer[]{1, 6, 6, 6, 5, 4, 3, 10, 1, null, null}));
        Integer[] collect = cal.stream().distinct().toArray(Integer[]::new);
        assertion(Arrays.equals(collect, new Integer[]{1, 6, 5, 4, 3, 10}));
        cal.sort(Comparator.comparingInt(Integer::intValue));
        assertion(Arrays.equals(cal.toArray(), new Integer[]{1, 1, 3, 4, 5, 6, 6, 6, 10}));
        cal.addAll(cal);
        cal.addAll(cal);
        assertion(cal.size() == 36);
        cal.add(null);
        assertion(cal.get(cal.size()-1) == null);
        cal.clear();
        assertion(cal.isEmpty());
        cal = new CustomArrayList<>(Arrays.stream(collect).toList());
        assertion(Arrays.equals(cal.toArray(), collect));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void testFile() {
        final String FILE_NAME = "test.txt";
        deleteFile(FILE_NAME);
        System.out.println("\nВсе выводы это часть теста, всё ок: ");
        assertion(File.read(Car.class, FILE_NAME).isEmpty());

        Car renault = Car.builder()
                .brand("Renault").model("Logan").year(2024).build();
        Car toyota = Car.builder()
                .brand("Toyota").model("Corolla").year(2023).build();
        Instant inst = Instant.now();
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(renault);
        cars.add(toyota);

        assertion(File.write(FILE_NAME, renault));
        assertionThrow(NoSuchElementException.class,() -> File.read(Instant.class, FILE_NAME).get());
        assertion(File.read(Car.class, FILE_NAME).get().equals(cars.get(0)));

        assertion(File.write(FILE_NAME, new Object[]{inst, toyota}));
        assertion(File.read(Car.class, FILE_NAME).get().equals(cars.get(1)));
        assertion(File.read(Instant.class, FILE_NAME).equals(Optional.of(inst)));

        assertion(File.write(FILE_NAME, new Object[]{renault, Instant.now(), toyota}));
        assertion(File.readAll(Car.class, FILE_NAME).equals(cars));

        assertion(File.write(FILE_NAME, cars));
        try (FileWriter fw = new FileWriter(FILE_NAME,true)) {
            fw.write(Arrays.toString(cars.toArray()));
        } catch (IOException ignored){
        }
        var list = File.readAll(CustomArrayList.class, FILE_NAME);
        assertion(list.size() == 1 && list.get(0).equals(cars));
        assertion(File.read(Car[].class, FILE_NAME).isEmpty());

        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            fw.write(Arrays.toString(cars.toArray()));
        } catch (IOException ignored) {
        }
        assertion(File.read(Car[].class, FILE_NAME).isEmpty());

        deleteFile(FILE_NAME);
    }

    private static void deleteFile(String fileName){
        File file = new File(fileName);
        file.delete();
    }

    public static void assertion(boolean statement){
        if (!statement) throw new AssertionError("Неверное утверждение");
    }

    public static <T extends Exception> void assertionThrow(Class<T> thr, Runnable statement){
        Exception exception = new Exception();
        try {
            statement.run();
        } catch (Exception e) {
            exception = e;
        }
        assertion(exception.getClass().equals(thr));
    }
}
