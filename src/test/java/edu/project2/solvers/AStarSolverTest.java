package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import edu.project2.Side;
import edu.project2.generators.GenerateAlgorithm;
import edu.project2.generators.BinaryTreeAlgorithm;
import edu.project2.generators.SidewinderAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class AStarSolverTest {
    static Arguments[] generators() {
        return new Arguments[] {
            Arguments.of(new BinaryTreeAlgorithm()),
            Arguments.of(new SidewinderAlgorithm())
        };
    }

    @ParameterizedTest()
    @MethodSource("generators")
    @DisplayName("Incorrect Value provided for source")
    void incorrectSourceValue(GenerateAlgorithm algorithm) {
        int width = 1;
        int height = 1;
        Maze maze = new Maze(width, height, algorithm);
        Solver solver = new AstarSolver();
        Position src = new Position(100, 2);
        Position dest = new Position(0, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> solver.findPath(maze, src, dest));
    }

    @ParameterizedTest()
    @MethodSource("generators")
    @DisplayName("Incorrect Value provided for destination")
    void incorrectDestValue(GenerateAlgorithm algorithm) {
        int width = 1;
        int height = 1;
        Maze maze = new Maze(width, height, algorithm);
        Solver solver = new AstarSolver();
        Position src = new Position(0, 0);
        Position dest = new Position(100, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> solver.findPath(maze, src, dest));
    }

    @ParameterizedTest()
    @MethodSource("generators")
    @DisplayName("Correct value provided and path exists")
    void correctValue_pathExists(GenerateAlgorithm algorithm) {
        int width = 5;
        int height = 3;
        Maze maze = new Maze(width, height, algorithm);
        Solver solver = new AstarSolver();
        Position src = new Position(0, 0);
        Position dest = new Position(4, 2);

        List<Cell> path = solver.findPath(maze, src, dest);

        assertThat(path).isNotNull();
    }
}
