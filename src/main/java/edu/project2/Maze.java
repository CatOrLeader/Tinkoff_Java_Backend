package edu.project2;

import edu.project2.generators.GenerateAlgorithm;
import org.jetbrains.annotations.Nullable;

public final class Maze {
    private final int width;
    private final int height;

    private final Cell[][] matrix;

    /**
     * Generate the maze using any of algorithms. If the algorithm is null, then maze is not filled with cells.
     *
     * @param width     width
     * @param height    height
     * @param generator if null - idle maze; otherwise, generating through usage of algorithm provided
     */
    public Maze(int width, int height, @Nullable GenerateAlgorithm generator) {
        if (!isValidSize(width, height)) {
            throw new IllegalArgumentException("Illegal arguments provided to the maze constructor");
        }
        this.width = width;
        this.height = height;
        matrix = new Cell[height][width];

        if (generator == null) {
            return;
        }

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
