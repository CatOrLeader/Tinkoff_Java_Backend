package edu.hw4;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class IncorrectBitePossibilityError implements ValidationError {
    private static final List<Animal.Type> CAN_BITE = List.of(Animal.Type.DOG, Animal.Type.CAT, Animal.Type.BIRD,
        Animal.Type.SPIDER
    );

    public static boolean isAppears(Animal animal) {
        return !CAN_BITE.contains(animal.type()) && animal.bites();
    }

    @Override
    public @NotNull String toString(@NotNull Animal animal) {
        return "Bite Possibility: incorrect byte possibility = " + animal.bites()
               + " for animal = " + animal.type().name() + " " + animal.name();
    }
}
