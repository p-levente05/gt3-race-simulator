package hu.levente.gt3.service;

import hu.levente.gt3.domain.IncidentSeverity;
import hu.levente.gt3.domain.RaceEntry;

import java.util.Objects;
import java.util.Random;

public class IncidentService {

    private final Random random;

    public IncidentService(Random random) {
        this.random = Objects.requireNonNull(random);
    }

    public String applyIncident(
            RaceEntry entry,
            IncidentSeverity severity
    ) {
        Objects.requireNonNull(entry);
        Objects.requireNonNull(severity);

        if (entry.isRetired()) {
            throw new IllegalStateException(
                    "Kiesett auto nem vehet reszt ujabb balesetben."
            );
        }

        String teamName = entry.getTeam().getName();

        return switch (severity) {
            case MINOR -> {
                double timeLoss = 2.0 + random.nextDouble() * 3.0;

                entry.addTimePenalty(timeLoss);

                yield String.format(
                        "%s: koccanás, +%.1f s.",
                        teamName,
                        timeLoss
                );
            }

            case MAJOR -> {
                double timeLoss = 5.0 + random.nextDouble() * 5.0;
                double damage = 0.5 + random.nextDouble() * 1.5;

                entry.addTimePenalty(timeLoss);
                entry.addDamage(damage);

                yield String.format(
                        "%s: serules, +%.1f s, tovabbi +%.1f s/kor.",
                        teamName,
                        timeLoss,
                        damage
                );
            }

            case TERMINAL -> {
                entry.retire();

                yield teamName + ": sulyos baleset, kiesett!";
            }
        };
    }
}