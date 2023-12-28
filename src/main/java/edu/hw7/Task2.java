package edu.hw7;

import java.util.stream.LongStream;

public final class Task2 {
    private Task2() {
    }

    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Incorrect n provided");
        }

        return LongStream.range(1, n + 1).parallel()
            .reduce((a, b) -> a * b).orElseThrow();
    }
}
