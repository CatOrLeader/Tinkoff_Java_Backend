package edu.project4.Transformations;

import edu.project4.Position;
import java.awt.Color;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public record AffineTransformation(double a, double b, double c, double d, double e, double f,
                                   @NotNull Color color)
    implements Function<Position, Position> {

    @Override
    public @NotNull Position apply(@NotNull Position position) {
        double x = position.x();
        double y = position.y();

        double newX = a * x + b * y + c;
        double newY = d * x + e * y + f;

        return new Position(newX, newY);
    }
}
