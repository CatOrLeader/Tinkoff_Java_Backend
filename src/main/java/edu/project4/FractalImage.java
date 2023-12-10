package edu.project4;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record FractalImage(Pixel[][] pixels, int width, int height) {
    @NotNull
    public static FractalImage create(int width, int height) {
        FractalImage image = new FractalImage(new Pixel[height][width], width, height);
        fill(image);
        return image;
    }

    public boolean contains(int x, int y) {
        return (0 <= x && x < width) && (0 <= y && y < height);
    }

    @Nullable
    public Pixel pixel(int x, int y) {
        return pixels[y][x];
    }

    private static void fill(FractalImage image) {
        var pixels = image.pixels;
        for (int y = 0; y < image.height; y++) {
            for (int x = 0; x < image.width; x++) {
                pixels[y][x] = new Pixel(
                    new Position(x, y),
                    new RGBConstants(0, 0, 0),
                    0, 0
                );
            }
        }
    }
}
