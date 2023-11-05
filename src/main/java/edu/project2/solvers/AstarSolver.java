package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import edu.project2.Side;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AstarSolver implements Solver {
    private static final List<Position> MOVES = List.of(
        new Position(1, 0), new Position(0, 1),
        new Position(-1, 0), new Position(0, -1)
    );
    private static final int PATH_WEIGHT = 1;

    @Override
    @Nullable public List<Cell> findPath(@NotNull Maze maze, @NotNull Position src, @NotNull Position dest) {
        if (!src.isIntoMaze(maze) || !dest.isIntoMaze(maze)) {
            throw new IllegalArgumentException("Incorrect source and destination provided");
        }

        Map<Position, Position> cameFrom = new HashMap<>();
        cameFrom.put(src, src);

        Map<Position, Integer> minPathFromInitial = new HashMap<>();
        minPathFromInitial.put(src, 0);

        Map<Position, Integer> bestGuess = new HashMap<>();
        bestGuess.put(src, Position.distance(src, dest));

        Queue<Position> queue = new PriorityQueue<>(Comparator.comparingInt(bestGuess::get));
        queue.add(src);

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (current.equals(dest)) {
                return constructPath(src, dest, cameFrom, maze.getMatrix());
            }

            List<Position> neighbours = neighbours(maze, current);
            for (Position neighbourPosition : neighbours) {
                if (!minPathFromInitial.containsKey(neighbourPosition)) {
                    minPathFromInitial.put(neighbourPosition, Integer.MAX_VALUE);
                }

                if (!bestGuess.containsKey(neighbourPosition)) {
                    bestGuess.put(neighbourPosition, Integer.MAX_VALUE);
                }

                int maybeNewScore = minPathFromInitial.get(current) + PATH_WEIGHT;
                if (maybeNewScore < minPathFromInitial.get(neighbourPosition)) {
                    cameFrom.put(neighbourPosition, current);
                    minPathFromInitial.put(neighbourPosition, maybeNewScore);
                    bestGuess.put(neighbourPosition, maybeNewScore + Position.distance(neighbourPosition, dest));

                    if (!queue.contains(neighbourPosition)) {
                        queue.add(neighbourPosition);
                    }
                }
            }
        }

        return null;
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
