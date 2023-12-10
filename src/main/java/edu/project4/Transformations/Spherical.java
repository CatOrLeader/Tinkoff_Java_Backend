package edu.project4.Transformations;

import edu.project4.Position;
import org.jetbrains.annotations.NotNull;

public final class Spherical implements Transformation {
    @Override
    public @NotNull Position apply(@NotNull Position position) {
        double x = position.getX();
        double y = position.getY();

        double denominator = (Math.pow(x, 2) + Math.pow(y, 2));

        double newX = x / denominator;
        double newY = y / denominator;

        return new Position(newX, newY);
    };
}
