package edu.project2.generators;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import org.jetbrains.annotations.NotNull;
import static edu.project2.generators.GenerateAlgorithmUtils.fillInitialCells;
import static edu.project2.generators.GenerateAlgorithmUtils.reverseFill;

public final class SidewinderAlgorithm implements GenerateAlgorithm {

    @Override
    public void generate(@NotNull Maze maze) {
        Cell[][] matrix = maze.getMatrix();
        int width = maze.getWidth();
        int height = maze.getHeight();

        fillInitialCells(matrix, width, height);

        for (int j = 0; j < width; j++) {
            if (matrix[0][j].position().isTouchRightBorder(maze)) {
                continue;
            }
            matrix[0][j].restrictedFrom().remove(Side.EAST);
        }

        for (int i = 1; i < height; i++) {
            List<Cell> set = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                Cell current = matrix[i][j];
                set.add(current);
                Choice decision = makeDecision();

                if (decision.equals(Choice.Stop)) {
                    Cell toOpenNorthWay = getRandom(set);
                    toOpenNorthWay.restrictedFrom().remove(Side.NORTH);
                    reverseFill(matrix, toOpenNorthWay.position(), Side.NORTH);
                    set.clear();
                } else {
                    if (current.position().isTouchRightBorder(maze)) {
                        current.restrictedFrom().remove(Side.NORTH);
                        reverseFill(matrix, current.position(), Side.NORTH);
                        continue;
                    }
                    current.restrictedFrom().remove(Side.EAST);
                    reverseFill(matrix, current.position(), Side.EAST);
                }
            }
        }
    }

    private Cell getRandom(List<Cell> set) {
        int index = Math.abs(RandomGenerator.getDefault().nextInt()) % set.size();
        return set.get(index);
    }

    private Choice makeDecision() {
        return (Math.abs(RandomGenerator.getDefault().nextInt()) % 2 == 0 ? Choice.Stop : Choice.GoRight);
    }

    private enum Choice {
        GoRight, Stop
    }
}
