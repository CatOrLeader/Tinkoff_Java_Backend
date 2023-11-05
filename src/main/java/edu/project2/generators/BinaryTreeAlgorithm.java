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

public final class BinaryTreeAlgorithm implements GenerateAlgorithm {
    private static final Position[] NEIGHBOURS_DELTAS = new Position[] {
        new Position(1, 0), new Position(0, -1)
    };

    private static final Position INITIAL = new Position(0, 0);

    @Override
    public void generate(@NotNull Maze maze) {
        Cell[][] matrix = maze.getMatrix();
        int width = maze.getWidth();
        int height = maze.getHeight();

        fillInitialCells(matrix, width, height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Position current = new Position(j, i);
                List<Side> possibleWallsToKnock = getPossibleWallsToKnock(maze, current);
                if (possibleWallsToKnock.isEmpty()) {
                    continue;
                }
                Side toKnock = getRandomWallToKnock(possibleWallsToKnock);
                matrix[i][j].restrictedFrom().remove(toKnock);

                reverseFill(matrix, current, toKnock);
            }
        }
    }

    private void reverseFill(Cell[][] matrix, Position current, Side knocked) {
        Position positionInKnockedDir = current.addition(knocked.getMove());
        Cell cellInKnockedDirection = matrix[positionInKnockedDir.getY()][positionInKnockedDir.getX()];
        Side knockedReversed = Side.parseSide(INITIAL.subtraction(knocked.getMove()));
        cellInKnockedDirection.restrictedFrom().remove(knockedReversed);
    }

    private Side getRandomWallToKnock(List<Side> possibleWallsToKnock) {
        int randomIndex = Math.abs(RandomGenerator.getDefault().nextInt()) % possibleWallsToKnock.size();
        return possibleWallsToKnock.get(randomIndex);
    }

    private List<Side> getPossibleWallsToKnock(Maze maze, Position position) {
        List<Side> possibleWalls = new ArrayList<>();

        if (position.isRightUpperCorner(maze)) {
            return possibleWalls;
        }

        for (Position delta : NEIGHBOURS_DELTAS) {
            Position wallSide = position.addition(delta);
            if (!wallSide.isIntoMaze(maze)
                || (position.isTouchRightBorder(maze) && delta.equals(Side.EAST.getMove()))) {
                continue;
            }
            possibleWalls.add(Side.parseSide(delta));
        }

        return possibleWalls;
    }

    private void fillInitialCells(Cell[][] matrix, int width, int height) {
        Side[] sides = new Side[] {Side.WEST, Side.SOUTH, Side.EAST, Side.NORTH};
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = new Cell(new ArrayList<>(Arrays.asList(sides)), new Position(j, i));
            }
        }
    }
}
