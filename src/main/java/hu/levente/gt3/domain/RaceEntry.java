package hu.levente.gt3.domain;

public class RaceEntry {

    private final Team team;
    private double totalTime;
    private int completedLaps;
    private double damagePenalty;
    private boolean retired;

    public RaceEntry(Team team) {
        if (team == null) {
            throw new IllegalArgumentException(
                    "A nevezeshez csapat szukseges."
            );
        }

        this.team = team;
    }

    public void completeLap(double lapTime) {
        ensureActive();

        if (!Double.isFinite(lapTime) || lapTime <= 0) {
            throw new IllegalArgumentException(
                    "A koridonek pozitiv, veges szamnak kell lennie."
            );
        }

        totalTime += lapTime + damagePenalty;
        completedLaps++;
    }

    public void addTimePenalty(double seconds) {
        ensureActive();
        validatePenalty(seconds);
        totalTime += seconds;
    }

    public void addDamage(double secondsPerLap) {
        ensureActive();
        validatePenalty(secondsPerLap);
        damagePenalty += secondsPerLap;
    }

    public void retire() {
        retired = true;
    }

    private void ensureActive() {
        if (retired) {
            throw new IllegalStateException(
                    "A kiesett auto mar nem versenyezhet."
            );
        }
    }

    private void validatePenalty(double seconds) {
        if (!Double.isFinite(seconds) || seconds < 0) {
            throw new IllegalArgumentException(
                    "Az idobuntetes nem lehet negativ vagy nem veges."
            );
        }
    }

    public Team getTeam() {
        return team;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public int getCompletedLaps() {
        return completedLaps;
    }

    public double getDamagePenalty() {
        return damagePenalty;
    }

    public boolean isRetired() {
        return retired;
    }
}