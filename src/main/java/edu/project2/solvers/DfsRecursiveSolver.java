package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static edu.project2.solvers.SolverUtils.constructPath;
import static edu.project2.solvers.SolverUtils.neighbours;

public final class DfsRecursiveSolver implements Solver {
    @Override
    public @Nullable List<Cell> findPath(@NotNull Maze maze, @NotNull Position src, @NotNull Position dest) {
        if (!src.isIntoMaze(maze) || !dest.isIntoMaze(maze)) {
            throw new IllegalArgumentException("Incorrect source and destination provided");
        }

        Set<Position> visited = Collections.synchronizedSet(new HashSet<>());
        Map<Position, Position> cameFrom = new HashMap<>();
        cameFrom.put(src, src);

        try (ForkJoinPool pool = ForkJoinPool.commonPool()) {
            return pool.invoke(
                new RecursiveDfsTask(src, src, dest, maze, cameFrom, visited)
            );
        }
    }

    private static final class RecursiveDfsTask extends RecursiveTask<List<Cell>> {
        private final Position src;
        private final Position current;
        private final Position dest;
        private final Maze maze;
        private final Map<Position, Position> cameFrom;
        private final Set<Position> visited;

        RecursiveDfsTask(
            Position src, Position current, Position dest, Maze maze, Map<Position, Position> cameFrom,
            Set<Position> visited
        ) {
            this.src = src;
            this.current = current;
            this.dest = dest;
            this.maze = maze;
            this.cameFrom = cameFrom;
            this.visited = visited;
        }

        @Override
        protected List<Cell> compute() {
            visited.add(current);

            if (current.equals(dest) || cameFrom.containsKey(dest)) {
                return constructPath(src, dest, cameFrom, maze.getMatrix());
            }

            List<RecursiveDfsTask> tasks = new ArrayList<>();
            for (Position neighbour : neighbours(maze, current)) {
                if (!visited.contains(neighbour)) {
                    cameFrom.put(neighbour, current);
                    tasks.add(
                        new RecursiveDfsTask(src, neighbour, dest, maze, new HashMap<>(cameFrom), visited)
                    );
                }
            }

            if (tasks.isEmpty()) {
                return null;
            }

            tasks.forEach(RecursiveTask::fork);
            List<Cell> path = null;
            for (RecursiveDfsTask task : tasks) {
                if (path != null) {
                    return path;
                }

                List<Cell> maybePath = task.join();
                if (maybePath != null) {
                    path = maybePath;
                }
            }

            return path;
        }
    }
}
