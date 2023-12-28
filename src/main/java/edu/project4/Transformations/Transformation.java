package edu.project4.Transformations;

import edu.project4.Position;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Transformation extends Function<Position, Position> {
    @Override
    @NotNull Position apply(@NotNull Position position);
}
