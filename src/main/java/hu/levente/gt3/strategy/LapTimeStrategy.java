package hu.levente.gt3.strategy;

import hu.levente.gt3.domain.Team;
import hu.levente.gt3.domain.Track;

import java.util.Random;

public interface LapTimeStrategy {

    double calculateLapTime(Team team, Track track, Random random);
}