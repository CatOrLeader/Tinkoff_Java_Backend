package edu.project2;

import edu.project2.generators.GenerateAlgorithm;
import org.jetbrains.annotations.NotNull;

public final class Maze {
    private final int width;
    private final int height;

    private final Cell[][] matrix;

    public Maze(int width, int height, @NotNull GenerateAlgorithm generator) {
        if (!isValidSize(width, height)) {
            throw new IllegalArgumentException("Illegal arguments provided to the maze constructor");
        }
        this.width = width;
        this.height = height;
        matrix = new Cell[height][width];
        accept(generator);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    private void accept(GenerateAlgorithm algorithm) {
        algorithm.generate(this);
    }

    private boolean isValidSize(int width, int height) {
        return width > 0 && height > 0;
    }
}
