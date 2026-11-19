package hu.levente.gt3.strategy;

import hu.levente.gt3.domain.Team;
import hu.levente.gt3.domain.Track;

import java.util.Random;

public class WetLapTimeStrategy implements LapTimeStrategy {

    @Override
    public double calculateLapTime(Team team, Track track, Random random) {
        double pacePenalty = (100 - team.getCar().getPace()) * 0.04;
        double skillPenalty = (100 - team.getDriver().getSkill()) * 0.15;
        double randomVariation = random.nextDouble() * 4.0;

        return track.getBaseLapTime() * 1.12
                + pacePenalty
                + skillPenalty
                + randomVariation;
    }
}