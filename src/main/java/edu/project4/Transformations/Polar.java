package edu.project4.Transformations;

import edu.project4.Position;
import org.jetbrains.annotations.NotNull;

public final class Polar implements Transformation {
    @Override
    public @NotNull Position apply(@NotNull Position position) {
        double x = position.getX();
        double y = position.getY();

        return new Position(Math.atan(y / x) / Math.PI, Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) - 1);
    }
}
