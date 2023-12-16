package edu.project4.Transformations;

import edu.project4.Position;
import org.jetbrains.annotations.NotNull;

public final class Heart implements Transformation {
    @Override
    public @NotNull Position apply(@NotNull Position position) {
        double x = position.x();
        double y = position.y();

        double part1 = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double part2 = part1 * Math.atan(y / x);

        double newX = part1 * Math.sin(part2);
        double newY = -part1 * Math.cos(part2);

        return new Position(newX, newY);
    }
}
