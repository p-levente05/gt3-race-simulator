package hu.levente.gt3.domain;

public class Track {

    private final String name;
    private final double baseLapTime;

    public Track(String name, double baseLapTime) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "A palya neve nem lehet ures."
            );
        }

        if (!Double.isFinite(baseLapTime) || baseLapTime <= 0) {
            throw new IllegalArgumentException(
                    "Az alap-koridonek pozitiv, veges szamnak kell lennie."
            );
        }

        this.name = name.strip();
        this.baseLapTime = baseLapTime;
    }

    public String getName() {
        return name;
    }

    public double getBaseLapTime() {
        return baseLapTime;
    }
}