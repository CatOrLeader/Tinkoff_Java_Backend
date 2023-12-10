package edu.project4;

public record RGBConstants(int r, int g, int b) {
    public static final int LOWER_BOUND = 0;
    public static final int UPPER_BOUND = 256;

    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;

    public int toRgb() {
        return (r << RED_SHIFT) | ((g + 1) << GREEN_SHIFT) | (b + 1);
    }

    @Override public String toString() {
        return "RGBConstants{"
               + "r=" + r
               + ", g=" + g
               + ", b=" + b
               + '}';
    }
}
