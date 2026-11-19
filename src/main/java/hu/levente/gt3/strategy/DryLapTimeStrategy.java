package hu.levente.gt3.strategy;

import hu.levente.gt3.domain.Team;
import hu.levente.gt3.domain.Track;

import java.util.Random;

public class DryLapTimeStrategy implements LapTimeStrategy {

    @Override
    public double calculateLapTime(Team team, Track track, Random random) {
        double pacePenalty = (100 - team.getCar().getPace()) * 0.08;
        double skillPenalty = (100 - team.getDriver().getSkill()) * 0.06;
        double randomVariation = random.nextDouble() * 2.0;

        return track.getBaseLapTime()
                + pacePenalty
                + skillPenalty
                + randomVariation;
    }
}