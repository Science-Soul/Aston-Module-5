package car;


import util.JsonSerializable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable, JsonSerializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String brand;
    private final String model;
    private final int year;

    private Car(Builder builder) {
        this.brand = builder.brand;
        this.model = builder.model;
        this.year = builder.year;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year);
    }

    @Override
    public String toString() {
        return "Машина: {" +
                "Бренд: " + brand +
                ", Модель: " + model +
                ", Год: " + year +
                '}';
    }

    /**
     * Представление машины в виде JSON-объекта: {"brand":"...","model":"...","year":...}.
     * Используется, например, {@link util.JsonFileLogger} при записи коллекций машин в файл.
     */
    @Override
    public String toJson() {
        return "{\"brand\":\"" + escape(brand) + "\","
                + "\"model\":\"" + escape(model) + "\","
                + "\"year\":" + year + "}";
    }

    private static String escape(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String brand;
        private String model;
        private int year;

        private Builder() {
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}