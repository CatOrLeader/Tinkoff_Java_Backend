package edu.hw4;

import org.jetbrains.annotations.NotNull;

public final class IncorrectAgeError implements ValidationError {
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 30;

    public static boolean isAppears(Animal animal) {
        return !(MIN_AGE <= animal.age() && animal.age() <= MAX_AGE);
    }

    @Override
    public @NotNull String toString(@NotNull Animal animal) {
        return "Age: incorrect age = " + animal.age()
               + " for animal = " + animal.type().name() + " " + animal.name();
    }
}
