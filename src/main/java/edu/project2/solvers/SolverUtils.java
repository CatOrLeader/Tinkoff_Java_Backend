package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import edu.project2.Side;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

final class SolverUtils {
    private SolverUtils() {
    }

    private static final List<Position> MOVES = List.of(
        new Position(1, 0), new Position(0, 1),
        new Position(-1, 0), new Position(0, -1)
    );

    static List<Cell> constructPath(
        Position src, Position dest, Map<Position, Position> cameFrom,
        Cell[][] matrix
    ) {
        List<Cell> path = new ArrayList<>();
        path.add(matrix[dest.getY()][dest.getX()]);
        Position current = dest;

        do {
            current = cameFrom.get(current);
            path.add(matrix[current.getY()][current.getX()]);
        } while (!current.equals(src));

        Collections.reverse(path);
        return path;
    }

    static List<Position> neighbours(Maze maze, Position current) {
        List<Position> neighbours = new ArrayList<>();

        Cell[][] matrix = maze.getMatrix();
        Cell currentCell = matrix[current.getY()][current.getX()];
        for (Position move : MOVES) {
            if (currentCell.restrictedFrom().contains(Side.parseSide(move))) {
                continue;
            }

            Position neighbourPosition = current.addition(move);
            neighbours.add(neighbourPosition);
        }

        return neighbours;
    }
}
