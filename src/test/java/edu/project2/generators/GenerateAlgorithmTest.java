package edu.project2.generators;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class GenerateAlgorithmTest {
    boolean isCorrectMaze(Maze maze) {
        Cell[][] matrix = maze.getMatrix();

        for (int j = 0; j < maze.getWidth(); j++) {
            if (!matrix[0][j].restrictedFrom().contains(Side.NORTH)
            || !matrix[maze.getHeight() - 1][j].restrictedFrom().contains(Side.SOUTH)) {
                return false;
            }
        }

        for (int i = 0; i < maze.getHeight(); i++) {
            if (!matrix[i][0].restrictedFrom().contains(Side.WEST)
            || !matrix[i][maze.getWidth() - 1].restrictedFrom().contains(Side.EAST)) {
                return false;
            }
        }

        return true;
    }

    static Arguments[] generators() {
        return new Arguments[]{
            Arguments.of(new BinaryTreeAlgorithm()),
            Arguments.of(new SidewinderAlgorithm())
        };
    }

    @ParameterizedTest()
    @MethodSource("generators")
    @DisplayName("1x1 Maze generation")
    void maze_1x1(GenerateAlgorithm algorithm) {
        int width = 1;
        int height = 1;
        Maze maze = new Maze(width, height, algorithm);

        assertThat(isCorrectMaze(maze)).isTrue();
    }

    @ParameterizedTest()
    @MethodSource("generators")
    @DisplayName("Arbitrary generated maze")
    void mazeArbitraryGenerated(GenerateAlgorithm algorithm) {
        int width = 5;
        int height = 7;
        Maze maze = new Maze(width, height, algorithm);

        assertThat(isCorrectMaze(maze)).isTrue();
    }
}
