package edu.project2;

import edu.project2.generators.GenerateAlgorithm;
import edu.project2.generators.SidewinderAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MazeTest {
    @Test
    @DisplayName("Incorrect width provided")
    void incorrectWidthProvided() {
        int width = -1;
        int height = 10;
        GenerateAlgorithm algorithm = new SidewinderAlgorithm();

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Maze(width, height, algorithm));
    }

    @Test
    @DisplayName("Incorrect height provided")
    void incorrectHeightProvided() {
        int width = 10;
        int height = -1;
        GenerateAlgorithm algorithm = new SidewinderAlgorithm();

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Maze(width, height, algorithm));
    }
}
