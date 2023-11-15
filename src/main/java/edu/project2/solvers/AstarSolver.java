package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static edu.project2.solvers.SolverUtils.constructPath;
import static edu.project2.solvers.SolverUtils.neighbours;

public final class AstarSolver implements Solver {
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
}
