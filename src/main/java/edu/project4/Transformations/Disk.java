package edu.project4.Transformations;

import edu.project4.Position;
import org.jetbrains.annotations.NotNull;

public final class Disk implements Transformation {
    @Override
    public @NotNull Position apply(@NotNull Position position) {
        double x = position.getX();
        double y = position.getY();

        double part1 = (1.0 / Math.PI) * Math.atan(y / x);
        double part2 = Math.PI * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        double newX = part1 * Math.sin(part2);
        double newY = part1 * Math.cos(part2);

        return new Position(newX, newY);
    }
}
