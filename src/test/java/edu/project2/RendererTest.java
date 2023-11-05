package edu.project2;

import edu.project2.generators.GenerateAlgorithm;
import edu.project2.generators.SidewinderAlgorithm;
import edu.project2.solvers.DfsSolver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import edu.project2.solvers.Solver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RendererTest {
    static String getContent(String string) {
        Set<Character> set = new HashSet<>();
        for (char ch : string.toCharArray()) {
            set.add(ch);
        }
        StringBuilder sb = new StringBuilder();
        for (char ch : set) {
            sb.append(ch);
        }

        return sb.toString();
    }

    @Test
    @DisplayName("1x1 maze rendered, without path")
    void maze1x1_withoutPath() {
        int width = 1;
        int height = 1;
        GenerateAlgorithm generator = new SidewinderAlgorithm();
        Maze maze = new Maze(width, height, generator);

        String actualValue = Renderer.render(maze, new ArrayList<>());
        String expectedValue = """
            +-+
            | |
            +-+""";

        assertThat(actualValue).containsIgnoringWhitespaces(expectedValue);
    }

    @Test
    @DisplayName("1x1 maze rendered, with path")
    void maze1x1_withPath() {
        int width = 1;
        int height = 1;
        GenerateAlgorithm generator = new SidewinderAlgorithm();
        Maze maze = new Maze(width, height, generator);
        Position src = new Position(0, 0);
        Position dest = new Position(0, 0);
        List<Cell> path = new DfsSolver().findPath(maze, src, dest);

        String actualValue = Renderer.render(maze, path);
        String expectedValue = """
            +-+
            |X|
            +-+""";

        assertThat(actualValue).containsIgnoringWhitespaces(expectedValue);
    }

    @Test
    @DisplayName("Arbitrary maze rendered, without path")
    void arbitraryMaze_withoutPath() {
        int width = 3;
        int height = 3;
        GenerateAlgorithm generator = new SidewinderAlgorithm();
        Maze maze = new Maze(width, height, generator);

        String actualValue = Renderer.render(maze, new ArrayList<>());
        String contentOfActualValue = getContent(actualValue);
        String expectedValue = """
                                A
                    |
                   +-+
                 <-| |->
                   +-+
                    |
                    V
            """;
        String contentOfExpectedValue = getContent(expectedValue);

        assertThat(contentOfActualValue.contentEquals(contentOfExpectedValue)).isTrue();
    }

    @Test
    @DisplayName("Arbitrary maze rendered, with path")
    void arbitraryMaze_withPath() {
        int width = 3;
        int height = 3;
        GenerateAlgorithm generator = new SidewinderAlgorithm();
        Maze maze = new Maze(width, height, generator);

        Solver solver = new DfsSolver();
        List<Cell> path = solver.findPath(maze, new Position(0, 0), new Position(2, 2));

        String actualValue = Renderer.render(maze, path);
        String contentOfActualValue = getContent(actualValue);
        String expectedValue = """
                                A
                    |
                   +-+
                 <-|X|->
                   +-+
                    |
                    V
            """;
        String contentOfExpectedValue = getContent(expectedValue);

        assertThat(contentOfActualValue.contentEquals(contentOfExpectedValue)).isTrue();
    }
}
