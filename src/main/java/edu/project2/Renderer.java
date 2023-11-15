package edu.project2;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class Renderer {
    private static final int STRING_CELL_SIZE = 7;
    private static final String CELL_UPPER_LOWER_PART = "+-+";
    private static final String CELL_MIDDLE_PART = "| |";
    private static final String PATH_CELL_MIDDLE_PART = "|X|";

    private Renderer() {
    }

    @SuppressWarnings({"checkstyle:CyclomaticComplexity", "checkstyle:MultipleStringLiterals",
        "checkstyle:MagicNumber"})
    public static @NotNull String render(@NotNull Maze maze, @NotNull List<Cell> path) {
        Cell[][] matrix = maze.getMatrix();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int level = 0; level < STRING_CELL_SIZE; level++) {

                if (level == 0) {
                    sb.append("   ");
                    for (int j = 0; j < maze.getWidth(); j++) {
                        if (!matrix[i][j].restrictedFrom().contains(Side.NORTH)) {
                            sb.append("A");
                        } else {
                            sb.append(" ");
                        }
                        sb.append(" ".repeat(9));
                    }
                }

                if (level == 1) {
                    sb.append("   ");
                    for (int j = 0; j < maze.getWidth(); j++) {
                        if (!matrix[i][j].restrictedFrom().contains(Side.NORTH)) {
                            sb.append("|");
                        } else {
                            sb.append(" ");
                        }
                        sb.append(" ".repeat(9));
                    }
                }

                if (level == 2 || level == 4) {
                    sb.append("  ");
                    for (int j = 0; j < maze.getWidth(); j++) {
                        sb.append(CELL_UPPER_LOWER_PART);
                        sb.append(" ".repeat(7));
                    }
                }

                if (level == 3) {
                    for (int j = 0; j < maze.getWidth(); j++) {
                        if (!matrix[i][j].restrictedFrom().contains(Side.WEST)) {
                            sb.append("<-");
                        } else {
                            sb.append("  ");
                        }

                        sb.append(
                            path.contains(matrix[i][j]) ? PATH_CELL_MIDDLE_PART : CELL_MIDDLE_PART
                        );

                        if (!matrix[i][j].restrictedFrom().contains(Side.EAST)) {
                            sb.append("->   ");
                        } else {
                            sb.append("     ");
                        }
                    }
                }

                if (level == 5) {
                    sb.append("   ");
                    for (int j = 0; j < maze.getWidth(); j++) {
                        if (!matrix[i][j].restrictedFrom().contains(Side.SOUTH)) {
                            sb.append("|");
                        } else {
                            sb.append(" ");
                        }
                        sb.append(" ".repeat(9));
                    }
                }

                if (level == 6) {
                    sb.append("   ");
                    for (int j = 0; j < maze.getWidth(); j++) {
                        if (!matrix[i][j].restrictedFrom().contains(Side.SOUTH)) {
                            sb.append("V");
                        } else {
                            sb.append(" ");
                        }
                        sb.append(" ".repeat(9));
                    }
                }

                sb.append("\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
