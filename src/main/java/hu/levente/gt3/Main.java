package hu.levente.gt3;

import hu.levente.gt3.domain.Car;
import hu.levente.gt3.domain.Driver;
import hu.levente.gt3.domain.Team;

import java.util.ArrayList;
import java.util.List;

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

        System.out.println("=== GT3 Race Simulator ===");
        System.out.println("Indulo csapatok:");

        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);

            System.out.println(
                    (i + 1) + ". " + team.getName()
                            + " | " + team.getDriver().getName()
                            + " | " + team.getCar().getModel()
            );
        }
    }
}