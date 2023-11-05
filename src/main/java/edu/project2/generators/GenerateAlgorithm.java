package edu.project2.generators;

import edu.project2.Maze;
import org.jetbrains.annotations.NotNull;

public sealed interface GenerateAlgorithm permits BinaryTreeAlgorithm, SidewinderAlgorithm {
    void generate(@NotNull Maze maze);
}
