package hu.levente.gt3.domain;

public class Car {

    private final String model;
    private final int pace;
    private final int reliability;

    public Car(String model, int pace, int reliability) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException(
                    "Az auto modellje nem lehet ures."
            );
        }

        if (pace < 1 || pace > 100) {
            throw new IllegalArgumentException(
                    "A temponak 1 es 100 kozott kell lennie."
            );
        }

        if (reliability < 1 || reliability > 100) {
            throw new IllegalArgumentException(
                    "A megbizhatosagnak 1 es 100 kozott kell lennie."
            );
        }

        this.model = model.strip();
        this.pace = pace;
        this.reliability = reliability;
    }

    public String getModel() {
        return model;
    }

    public int getPace() {
        return pace;
    }

    public int getReliability() {
        return reliability;
    }
}