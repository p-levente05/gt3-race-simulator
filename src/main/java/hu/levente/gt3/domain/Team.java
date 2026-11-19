package hu.levente.gt3.domain;

public class Team {

    private final String name;
    private final Driver driver;
    private final Car car;

    public Team(String name, Driver driver, Car car) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "A csapat neve nem lehet ures."
            );
        }

        if (driver == null) {
            throw new IllegalArgumentException(
                    "A csapatnak kell versenyzo."
            );
        }

        if (car == null) {
            throw new IllegalArgumentException(
                    "A csapatnak kell auto."
            );
        }

        this.name = name.strip();
        this.driver = driver;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public Driver getDriver() {
        return driver;
    }

    public Car getCar() {
        return car;
    }
}