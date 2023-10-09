package edu.hw1;

import java.util.Arrays;
import java.util.InputMismatchException;

public final class Task8 {
    private static final int BOARD_SIZE = 8;
    private static final int[][] POSSIBLE_MOVES = {{1, 2}, {-1, 2}, {1, -2}, {-1, -2},
        {2, 1}, {-2, 1}, {2, -1}, {-2, -1}};

    private Task8() {
    }

    public static boolean knightBoardCapture(int[][] board) throws InputMismatchException {
        if (board.length != BOARD_SIZE || Arrays.stream(board).anyMatch(arr -> arr.length != BOARD_SIZE)) {
            throw new InputMismatchException();
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 1) {
                    if (isCaptured(i, j, board)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static boolean isCaptured(int n, int m, int[][] board) {
        for (int[] move : POSSIBLE_MOVES) {
            int curN = n + move[0];
            int curM = m + move[1];

            if (isOnBoard(curN, curM)) {
                if (board[curN][curM] == 1) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isOnBoard(int n, int m) {
        return ((0 <= n && n <= BOARD_SIZE - 1) && (0 <= m && m <= BOARD_SIZE - 1));
    }
}
