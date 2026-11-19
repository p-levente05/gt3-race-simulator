package hu.levente.gt3.service;

import hu.levente.gt3.domain.IncidentSeverity;
import hu.levente.gt3.domain.RaceEntry;
import hu.levente.gt3.domain.Team;
import hu.levente.gt3.domain.Track;
import hu.levente.gt3.strategy.LapTimeStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RaceSimulator {

    private final Random random;
    private final IncidentService incidentService;
    private final List<String> events = new ArrayList<>();

    public RaceSimulator(Random random) {
        this.random = Objects.requireNonNull(random);
        this.incidentService = new IncidentService(random);
    }

    public List<RaceEntry> simulate(
            List<Team> teams,
            Track track,
            int laps,
            LapTimeStrategy strategy,
            boolean wetWeather
    ) {
        if (teams == null || teams.isEmpty()) {
            throw new IllegalArgumentException(
                    "Legalabb egy csapat szukseges."
            );
        }

        if (track == null || strategy == null) {
            throw new IllegalArgumentException(
                    "Palya es korido-strategia szukseges."
            );
        }

        if (laps < 1) {
            throw new IllegalArgumentException(
                    "A korszamnak pozitivnak kell lennie."
            );
        }

        events.clear();

        List<RaceEntry> entries = new ArrayList<>();

        for (Team team : teams) {
            entries.add(new RaceEntry(team));
        }

        for (int lap = 1; lap <= laps; lap++) {
            for (RaceEntry entry : entries) {
                if (!entry.isRetired()) {
                    double lapTime = strategy.calculateLapTime(
                            entry.getTeam(),
                            track,
                            random
                    );

                    entry.completeLap(lapTime);
                }
            }

            checkCollisions(entries, lap, wetWeather);
        }

        entries.sort(
                Comparator.comparing(RaceEntry::isRetired)
                        .thenComparing(
                                Comparator.comparingInt(
                                        RaceEntry::getCompletedLaps
                                ).reversed()
                        )
                        .thenComparingDouble(RaceEntry::getTotalTime)
        );

        return entries;
    }

    private void checkCollisions(
            List<RaceEntry> entries,
            int lap,
            boolean wetWeather
    ) {
        List<RaceEntry> activeEntries = new ArrayList<>();

        for (RaceEntry entry : entries) {
            if (!entry.isRetired()) {
                activeEntries.add(entry);
            }
        }

        activeEntries.sort(
                Comparator.comparingDouble(RaceEntry::getTotalTime)
        );

        for (int i = 0; i < activeEntries.size() - 1; i++) {
            RaceEntry first = activeEntries.get(i);
            RaceEntry second = activeEntries.get(i + 1);

            double gap = Math.abs(
                    second.getTotalTime() - first.getTotalTime()
            );

            if (gap > 1.5) {
                continue;
            }

            double averageAggression = (
                    first.getTeam().getDriver().getAggression()
                            + second.getTeam().getDriver().getAggression()
            ) / 2.0;

            double averageSkill = (
                    first.getTeam().getDriver().getSkill()
                            + second.getTeam().getDriver().getSkill()
            ) / 2.0;

            double collisionChance = 0.02
                    + averageAggression * 0.0005
                    + (100 - averageSkill) * 0.0003;

            if (wetWeather) {
                collisionChance *= 1.7;
            }

            if (random.nextDouble() < collisionChance) {
                events.add(
                        lap + ". kor: Utkozes! "
                                + first.getTeam().getName()
                                + " es "
                                + second.getTeam().getName()
                );

                events.add(incidentService.applyIncident(
                        first,
                        randomSeverity()
                ));

                events.add(incidentService.applyIncident(
                        second,
                        randomSeverity()
                ));

                // Egy auto koronként legfeljebb egy utkozesben szerepel.
                i++;
            }
        }
    }

    private IncidentSeverity randomSeverity() {
        double roll = random.nextDouble();

        if (roll < 0.10) {
            return IncidentSeverity.TERMINAL;
        }

        if (roll < 0.40) {
            return IncidentSeverity.MAJOR;
        }

        return IncidentSeverity.MINOR;
    }

    public List<String> getEvents() {
        return List.copyOf(events);
    }
}