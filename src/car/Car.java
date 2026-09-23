package car;

public class Car {
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
    public String toString() {
        return "Машина: {" +
                "Бренд: " + brand +
                ", Модель: " + model +
                ", Год: " + year +
                '}';
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