package edu.project2.generators;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import edu.project2.Side;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;
import org.jetbrains.annotations.NotNull;

public final class SidewinderAlgorithm implements GenerateAlgorithm {
    private static final Position INITIAL = new Position(0, 0);

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

    private void reverseFill(Cell[][] matrix, Position current, Side knocked) {
        Position positionInKnockedDir = current.addition(knocked.getMove());
        Cell cellInKnockedDirection = matrix[positionInKnockedDir.getY()][positionInKnockedDir.getX()];
        Side knockedReversed = Side.parseSide(INITIAL.subtraction(knocked.getMove()));
        cellInKnockedDirection.restrictedFrom().remove(knockedReversed);
    }

    private Cell getRandom(List<Cell> set) {
        int index = Math.abs(RandomGenerator.getDefault().nextInt()) % set.size();
        return set.get(index);
    }

    private Choice makeDecision() {
        return (Math.abs(RandomGenerator.getDefault().nextInt()) % 2 == 0 ? Choice.Stop : Choice.GoRight);
    }

    private void fillInitialCells(Cell[][] matrix, int width, int height) {
        Side[] sides = new Side[] {Side.WEST, Side.SOUTH, Side.EAST, Side.NORTH};
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = new Cell(new ArrayList<>(Arrays.asList(sides)), new Position(j, i));
            }
        }
    }

    private enum Choice {
        GoRight, Stop
    }
}
