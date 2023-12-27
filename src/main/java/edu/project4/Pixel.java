package edu.project4;

import java.awt.Color;
import org.jetbrains.annotations.NotNull;

public final class Pixel {
    private Position position;
    private Color color;
    private int hits;
    private double normal;

    public Pixel(@NotNull Position position, @NotNull Color color, int hits) {
        this.position = position;
        this.color = color;
        this.hits = hits;
        this.normal = 0;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public synchronized Color getColor() {
        return color;
    }

    public synchronized void setColor(Color color) {
        this.color = color;
    }

    public synchronized int getHits() {
        return hits;
    }

    public synchronized void setHits(int hits) {
        this.hits = hits;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

    public static Pixel getDefault(@NotNull Position position) {
        return new Pixel(
            position,
            Color.BLACK,
            0
        );
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pixel pixel = (Pixel) o;

        if (hits != pixel.hits) {
            return false;
        }
        if (Double.compare(normal, pixel.normal) != 0) {
            return false;
        }
        if (!position.equals(pixel.position)) {
            return false;
        }
        return color.equals(pixel.color);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = position.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + hits;
        temp = Double.doubleToLongBits(normal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
