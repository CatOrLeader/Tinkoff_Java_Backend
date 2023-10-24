package edu.hw4;

import org.jetbrains.annotations.NotNull;

public final class IncorrectWeightError implements ValidationError {
    public static boolean isAppears(Animal animal) {
        return animal.weight() <= 0;
    }

    @Override
    public @NotNull String toString(@NotNull Animal animal) {
        return "Weight: incorrect weight = " + animal.weight()
               + " for animal = " + animal.type().name() + " " + animal.name();
    }
}
