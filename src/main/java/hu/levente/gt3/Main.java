package hu.levente.gt3;

import hu.levente.gt3.domain.Car;
import hu.levente.gt3.domain.Driver;
import hu.levente.gt3.domain.RaceEntry;
import hu.levente.gt3.domain.Team;
import hu.levente.gt3.domain.Track;
import hu.levente.gt3.service.RaceSimulator;
import hu.levente.gt3.strategy.DryLapTimeStrategy;
import hu.levente.gt3.strategy.LapTimeStrategy;
import hu.levente.gt3.strategy.WetLapTimeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        List<Team> teams = new ArrayList<>();

        teams.add(new Team(
                "Panther Racing",
                new Driver("Ethan", 85, 60),
                new Car("Porsche 911 GT3 R", 88, 90)
        ));

        teams.add(new Team(
                "Bavarian Motorsport",
                new Driver("Daniel", 88, 70),
                new Car("BMW M4 GT3", 87, 92)
        ));

        teams.add(new Team(
                "Silver Star Racing",
                new Driver("Mark", 91, 55),
                new Car("Mercedes-AMG GT3", 89, 91)
        ));

        teams.add(new Team(
                "Redline Corse",
                new Driver("Alex", 86, 85),
                new Car("Ferrari 296 GT3", 92, 85)
        ));

        teams.add(new Team(
                "Emerald Racing",
                new Driver("Oliver", 84, 50),
                new Car("Aston Martin Vantage GT3", 86, 94)
        ));

        teams.add(new Team(
                "Raging Bull Motorsport",
                new Driver("Adam", 89, 90),
                new Car("Lamborghini Huracan GT3 EVO2", 91, 84)
        ));

        List<Track> tracks = new ArrayList<>();

        tracks.add(new Track("Monza", 110.0));
        tracks.add(new Track("Spa-Francorchamps", 140.0));
        tracks.add(new Track("Hungaroring", 105.0));

        Track selectedTrack = tracks.get(0);
        int laps = 10;
        boolean wetWeather = true;

        LapTimeStrategy strategy = wetWeather
                ? new WetLapTimeStrategy()
                : new DryLapTimeStrategy();

        RaceSimulator simulator = new RaceSimulator(new Random());

        System.out.println("=== GT3 Race Simulator ===");
        System.out.println("Palya: " + selectedTrack.getName());
        System.out.println("Korszam: " + laps);
        System.out.println(
                "Idojaras: " + (wetWeather ? "esos" : "szaraz")
        );

        List<RaceEntry> results = simulator.simulate(
                teams,
                selectedTrack,
                laps,
                strategy,
                wetWeather
        );

        System.out.println();
        System.out.println("=== Versenyesemenyek ===");

        if (simulator.getEvents().isEmpty()) {
            System.out.println("A futam baleset nelkul zajlott.");
        } else {
            for (String event : simulator.getEvents()) {
                System.out.println(event);
            }
        }

        System.out.println();
        System.out.println("=== Vegeredmeny ===");

        RaceEntry winner = null;

        for (RaceEntry entry : results) {
            if (!entry.isRetired()) {
                winner = entry;
                break;
            }
        }

        int position = 1;

        for (RaceEntry entry : results) {
            if (entry.isRetired()) {
                System.out.printf(
                        "DNF | %s | %s | Teljesitett korok: %d%n",
                        entry.getTeam().getName(),
                        entry.getTeam().getDriver().getName(),
                        entry.getCompletedLaps()
                );
            } else {
                double gap = entry.getTotalTime() - winner.getTotalTime();

                System.out.printf(
                        "%d. %s | %s | %.3f s | +%.3f s%n",
                        position,
                        entry.getTeam().getName(),
                        entry.getTeam().getDriver().getName(),
                        entry.getTotalTime(),
                        gap
                );

                position++;
            }
        }

        System.out.println();

        if (winner == null) {
            System.out.println("Minden auto kiesett, nincs gyoztes.");
        } else {
            System.out.println(
                    "Gyoztes: " + winner.getTeam().getName()
            );
        }
    }
}