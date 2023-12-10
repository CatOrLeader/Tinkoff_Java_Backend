package edu.project4;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class Position {
    private final double x;
    private final double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    public static Position sum(@NotNull Position... positions) {
        double newX = 0;
        double newY = 0;
        for (Position position : positions) {
            newX += position.getX();
            newY += position.getY();
        }
        return new Position(newX, newY);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Double.compare(x, position.x) == 0 && Double.compare(y, position.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
