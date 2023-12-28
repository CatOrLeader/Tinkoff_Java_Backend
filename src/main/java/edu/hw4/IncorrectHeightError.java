package edu.hw4;

import org.jetbrains.annotations.NotNull;

public final class IncorrectHeightError implements ValidationError {
    public static boolean isAppears(Animal animal) {
        return animal.height() <= 0;
    }

    @Override
    public @NotNull String toString(@NotNull Animal animal) {
        return "Height: incorrect height = " + animal.height()
               + " for animal = " + animal.type().name() + " " + animal.name();
    }
}
