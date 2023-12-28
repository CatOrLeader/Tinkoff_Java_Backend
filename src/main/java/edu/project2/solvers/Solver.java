package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.Position;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface Solver permits DfsRecursiveSolver, AstarSolver, DfsSolver {
    @Nullable List<Cell> findPath(@NotNull Maze maze, @NotNull Position src, @NotNull Position dest);
}
