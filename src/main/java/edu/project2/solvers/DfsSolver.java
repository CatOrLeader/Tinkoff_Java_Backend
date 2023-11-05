package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import edu.project2.Side;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DfsSolver implements Solver {
    private static final List<Position> MOVES = List.of(
        new Position(1, 0), new Position(0, 1),
        new Position(-1, 0), new Position(0, -1)
    );

    @Override
    public @Nullable List<Cell> findPath(@NotNull Maze maze, @NotNull Position src, @NotNull Position dest) {
        if (!src.isIntoMaze(maze) || !dest.isIntoMaze(maze)) {
            throw new IllegalArgumentException("Incorrect source and destination provided");
        }

        Set<Position> visited = new HashSet<>();

        Stack<Position> stack = new Stack<>();
        stack.push(src);

        Map<Position, Position> cameFrom = new HashMap<>();
        cameFrom.put(src, src);

        while (!stack.isEmpty()) {
            Position current = stack.pop();
            visited.add(current);

            for (Position neighboursPosition : neighbours(maze, current)) {
                if (!visited.contains(neighboursPosition)) {
                    stack.push(neighboursPosition);
                    cameFrom.put(neighboursPosition, current);
                }
            }
        }

        if (!cameFrom.containsKey(dest)) {
            return null;
        }

        return constructPath(src, dest, cameFrom, maze.getMatrix());
    }

    private List<Cell> constructPath(Position src, Position dest, Map<Position, Position> cameFrom,
        Cell[][] matrix) {
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

    private List<Position> neighbours(Maze maze, Position current) {
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
