package edu.hw4;

import java.util.Set;
import org.jetbrains.annotations.NotNull;


public sealed interface ValidationError
    permits IncorrectBitePossibilityError, IncorrectAgeError, IncorrectHeightError, IncorrectWeightError {
    static boolean containsErrors(@NotNull Animal animal) {
        return IncorrectBitePossibilityError.isAppears(animal)
               || IncorrectAgeError.isAppears(animal)
               || IncorrectHeightError.isAppears(animal)
               || IncorrectWeightError.isAppears(animal);
    }

    static @NotNull String merge(Set<ValidationError> errors, Animal animal) {
        StringBuilder builder = new StringBuilder();
        for (ValidationError error : errors) {
            builder.append(error.toString(animal)).append("; ");
        }
        return builder.toString();
    }

    @NotNull String toString(@NotNull Animal animal);
}
