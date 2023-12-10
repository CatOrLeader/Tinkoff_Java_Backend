package edu.project4;

import org.jetbrains.annotations.NotNull;

public final class Pixel {
    private Position position;
    private RGBConstants rgb;
    private int hits;
    private double normal;

    public Pixel(@NotNull Position position, @NotNull RGBConstants rgb, int hits, int normal) {
        this.position = position;
        this.rgb = rgb;
        this.hits = hits;
        this.normal = 0;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public RGBConstants getRgb() {
        return rgb;
    }

    public void setRgb(RGBConstants rgb) {
        this.rgb = rgb;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }
}
