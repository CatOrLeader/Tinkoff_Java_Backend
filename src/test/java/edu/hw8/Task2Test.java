package edu.hw8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw8.Task2.ThreadPool;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;

public class Task2Test {
    @Test
    @DisplayName("Incorrect pool size")
    void incorrectPoolSize() {
        int size = -1;

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                try (ThreadPool ignored = Task2.FixedThreadPool.create(size)) {
                    fail("");
                }
            });
    }

    @Test
    @DisplayName("Null task")
    void nullTask() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> {
                try (ThreadPool pool = Task2.FixedThreadPool.create(1)) {
                    pool.execute(null);
                }
            });
    }

    @Test
    @DisplayName("Insertion of the task after termination")
    void insertionAfterTermination() {
        Runnable runnable = () -> System.out.println("I'm stupid task");

        assertThatExceptionOfType(RejectedExecutionException.class)
            .isThrownBy(() -> {
                ThreadPool pool = Task2.FixedThreadPool.create(1);
                pool.execute(runnable);
                pool.start();
                pool.close();
                pool.execute(runnable);
            });
    }

    @Test
    @DisplayName("Simple pool testing")
    void simplePoolTesting() throws Exception {
        final int threadCount = 10;
        List<String> actualValue = Collections.synchronizedList(new ArrayList<>());

        try (ThreadPool pool = Task2.FixedThreadPool.create(5)) {
            for (int i = 0; i < threadCount; i++) {
                pool.execute(() -> actualValue.add("bob"));
            }
            pool.start();
        }
        List<String> expectedValues = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            expectedValues.add("bob");
        }

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValues);
    }

    @Test
    @DisplayName("Multithreading fibonacci testing")
    void multithreadingFibonacciTesting() throws Exception {
        final int threadsCount = 30;
        AtomicInteger current = new AtomicInteger(1);
        List<Integer> actualValues = Collections.synchronizedList(new ArrayList<>());
        try (ThreadPool pool = Task2.FixedThreadPool.create(5)) {
            for (int i = 0; i < threadsCount; i++) {
                pool.execute(() -> {
                    int answer = fibonacci(current.incrementAndGet());
                    actualValues.add(answer);
                });
            }
            pool.start();
        }
        Thread.sleep(100);

        List<Integer> expectedValues = List.of(
            1,
            2,
            3,
            5,
            8,
            13,
            21,
            34,
            55,
            89,
            144,
            233,
            377,
            610,
            987,
            1597,
            2584,
            4181,
            6765,
            10946,
            17711,
            28657,
            46368,
            75025,
            121393,
            196418,
            317811,
            514229,
            832040,
            1346269
        );

        assertThat(actualValues).containsExactlyInAnyOrderElementsOf(expectedValues);
    }

    private static int fibonacci(int n) {
        if (n <= 2) {
            return 1;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
