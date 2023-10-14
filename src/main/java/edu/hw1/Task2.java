package edu.hw1;

public final class Task2 {
    private static final int DELIMITER = 10;

    private Task2() {}

    public static int countDigits(long num) {
        if (num == 0) {
            return 1;
        }

        int count = 0;
        long temp = num;
        while (temp != 0) {
            temp /= DELIMITER;
            count++;
        }

        return count;
    }
}
