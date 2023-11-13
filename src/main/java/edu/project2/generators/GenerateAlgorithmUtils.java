package edu.project2.generators;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import edu.project2.Side;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GenerateAlgorithmUtils {
    private GenerateAlgorithmUtils() {
    }

    private static final Position INITIAL = new Position(0, 0);

    static void fillInitialCells(Cell[][] matrix, int width, int height) {
        Side[] sides = new Side[] {Side.WEST, Side.SOUTH, Side.EAST, Side.NORTH};
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = new Cell(new ArrayList<>(Arrays.asList(sides)), new Position(j, i));
            }
        }
    }

    static void reverseFill(Cell[][] matrix, Position current, Side knocked) {
        Position positionInKnockedDir = current.addition(knocked.getMove());
        Cell cellInKnockedDirection = matrix[positionInKnockedDir.getY()][positionInKnockedDir.getX()];
        Side knockedReversed = Side.parseSide(INITIAL.subtraction(knocked.getMove()));
        cellInKnockedDirection.restrictedFrom().remove(knockedReversed);
    }

    public static void fillFromScratch(Maze maze, List<Cell> cells) {
        Cell[][] matrix = maze.getMatrix();
        int width = maze.getWidth();
        int height = maze.getHeight();

        fillInitialCells(matrix, width, height);

        for (Cell cell : cells) {
            Position position = cell.position();

            if (isIncorrectCell(maze, cell) || !position.isIntoMaze(maze)) {
                throw new IllegalArgumentException("Incorrect cell provided");
            }

            matrix[position.getY()][position.getX()] = cell;
        }
    }

    private static boolean isIncorrectCell(Maze maze, Cell cell) {
        Position position = cell.position();

        return (position.isRightUpperCorner(maze) && !cell.restrictedFrom().contains(Side.NORTH))
               || (position.isRightUpperCorner(maze) && !cell.restrictedFrom().contains(Side.EAST))
               || (position.isTouchRightBorder(maze) && !cell.restrictedFrom().contains(Side.EAST));
    }
}
