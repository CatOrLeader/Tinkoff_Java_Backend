package edu.project4.Transformations;

import edu.project4.Position;
import org.jetbrains.annotations.NotNull;

public final class Sinusoidal implements Transformation {
    @Override
    public @NotNull Position apply(@NotNull Position position) {
        return new Position(Math.sin(position.getX()), Math.sin(position.getY()));
    }
}
