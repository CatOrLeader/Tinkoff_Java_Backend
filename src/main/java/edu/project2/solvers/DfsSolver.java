package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static edu.project2.solvers.SolverUtils.constructPath;
import static edu.project2.solvers.SolverUtils.neighbours;

public final class DfsSolver implements Solver {
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
}
