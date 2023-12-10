package edu.project4.Transformations;

import edu.project4.Position;
import edu.project4.RGBConstants;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public record AffineTransformation(double a, double b, double c, double d, double e, double f,
                                   @NotNull RGBConstants rgb)
    implements Function<Position, Position> {

    @Override
    public @NotNull Position apply(@NotNull Position position) {
        double x = position.getX();
        double y = position.getY();

        double newX = a * x + b * y + c;
        double newY = d * x + e * y + f;

        return new Position(newX, newY);
    }
}
