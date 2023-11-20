package edu.hw7;

import java.util.ArrayList;
import java.util.List;

public final class Task2 {
    private Task2() {
    }

    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Incorrect n provided");
        }

        List<Long> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbers.add((long) i);
        }

        return numbers.parallelStream()
            .reduce((a, b) -> a * b).orElseThrow();
    }
}
