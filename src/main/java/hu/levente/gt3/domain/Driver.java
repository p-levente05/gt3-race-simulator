package hu.levente.gt3.domain;

public class Driver {

    private final String name;
    private final int skill;
    private final int aggression;

    public Driver(String name, int skill, int aggression) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "A versenyzo neve nem lehet ures."
            );
        }

        if (skill < 1 || skill > 100) {
            throw new IllegalArgumentException(
                    "A kepessegnek 1 es 100 kozott kell lennie."
            );
        }

        if (aggression < 1 || aggression > 100) {
            throw new IllegalArgumentException(
                    "Az agresszivitasnak 1 es 100 kozott kell lennie."
            );
        }

        this.name = name.strip();
        this.skill = skill;
        this.aggression = aggression;
    }

    public String getName() {
        return name;
    }

    public int getSkill() {
        return skill;
    }

    public int getAggression() {
        return aggression;
    }
}