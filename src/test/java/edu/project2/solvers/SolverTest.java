package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import edu.project2.Side;
import edu.project2.generators.GenerateAlgorithmUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class SolverTest {
    static Arguments[] solvers() {
        return new Arguments[]{
            Arguments.of(new AstarSolver()),
            Arguments.of(new DfsSolver()),
            Arguments.of(new DfsRecursiveSolver())
        };
    }

    private static final Side[] sides = new Side[] {Side.WEST, Side.SOUTH, Side.EAST, Side.NORTH};

    private static List<Cell> defaultCells() {
        return List.of(
            new Cell(Arrays.asList(
                Side.WEST, Side.NORTH, Side.EAST
            ), new Position(0, 0)),

            new Cell(Arrays.asList(
                Side.WEST, Side.NORTH, Side.EAST
            ), new Position(1, 0)),

            new Cell(Arrays.asList(
                Side.WEST, Side.SOUTH
            ), new Position(0, 1)),

            new Cell(Arrays.asList(
                Side.SOUTH, Side.EAST
            ), new Position(1, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("solvers")
    @DisplayName("Incorrect Value provided for source")
    void incorrectSourceValue(Solver algorithm) {
        int width = 1;
        int height = 1;
        Maze maze = new Maze(width, height, null);
        GenerateAlgorithmUtils.fillFromScratch(maze, List.of(
            new Cell(Arrays.asList(sides), new Position(0, 0))
        ));
        Position src = new Position(100, 2);
        Position dest = new Position(0, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> algorithm.findPath(maze, src, dest));
    }

    @ParameterizedTest
    @MethodSource("solvers")
    @DisplayName("Incorrect Value provided for destination")
    void incorrectDestValue(Solver algorithm) {
        int width = 1;
        int height = 1;
        Maze maze = new Maze(width, height, null);
        GenerateAlgorithmUtils.fillFromScratch(maze, List.of(
            new Cell(Arrays.asList(sides), new Position(0, 0))
        ));
        Position src = new Position(0, 0);
        Position dest = new Position(100, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> algorithm.findPath(maze, src, dest));
    }

    @ParameterizedTest
    @MethodSource("solvers")
    @DisplayName("Correct value provided and path exists, single path")
    void correctValue_singlePathExists(Solver algorithm) {
        int width = 2;
        int height = 2;
        Maze maze = new Maze(width, height, null);
        GenerateAlgorithmUtils.fillFromScratch(maze, defaultCells());
        Position src = new Position(0, 0);
        Position dest = new Position(1, 0);

        List<Cell> actualPath = algorithm.findPath(maze, src, dest);
        List<Cell> expectedPath = new ArrayList<>();
        expectedPath.add(defaultCells().get(0));
        expectedPath.add(defaultCells().get(2));
        expectedPath.add(defaultCells().get(3));
        expectedPath.add(defaultCells().get(1));

        assertThat(actualPath).containsExactlyElementsOf(expectedPath);
    }

    @ParameterizedTest
    @MethodSource("solvers")
    @DisplayName("Correct value provided and path exists, multiple paths")
    void correctValue_multiplePathsExist(Solver algorithm) {
        int width = 2;
        int height = 2;
        Maze maze = new Maze(width, height, null);
        GenerateAlgorithmUtils.fillFromScratch(maze, defaultCells());
        Position src = new Position(0, 0);
        Position dest = new Position(0, 1);

        List<Cell> actualPath = algorithm.findPath(maze, src, dest);
        List<Cell> expectedPath = new ArrayList<>();
        expectedPath.add(defaultCells().get(0));
        expectedPath.add(defaultCells().get(2));

        assertThat(actualPath).containsExactlyElementsOf(expectedPath);
    }
}
