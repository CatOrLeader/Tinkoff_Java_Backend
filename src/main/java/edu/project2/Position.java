package edu.project2;

import org.jetbrains.annotations.NotNull;

public final class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isIntoMaze(Maze maze) {
        return 0 <= x && x <= maze.getWidth() && 0 <= y && y < maze.getHeight();
    }

    public boolean isRightUpperCorner(Maze maze) {
        return isTouchRightBorder(maze) && y == 0;
    }

    public boolean isTouchRightBorder(Maze maze) {
        return x == maze.getWidth() - 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public @NotNull Position addition(@NotNull Position other) {
        return new Position(this.x + other.getX(), this.y + other.getY());
    }

    public @NotNull Position subtraction(@NotNull Position other) {
        return new Position(this.x - other.getX(), this.y - other.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Position castedObj = (Position) obj;
        return this.x == castedObj.x && this.y == castedObj.y;
    }

    public static int distance(Position pos1, Position pos2) {
        return Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.x;
        result = prime * result + this.y;
        return result;
    }

    @Override
    public String toString() {
        return "Position: x=" + this.x + "; y=" + this.y;
    }
}
