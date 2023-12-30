package edu.hw8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw8.Task2.ThreadPool;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task2Test {
    @Test
    @DisplayName("Incorrect pool size")
    void incorrectPoolSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> tryToCreateThreadPoolWithIncorrectSize(-1));
    }

    @Test
    @DisplayName("Null task")
    void nullTask() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> tryToCreateThreadPoolWithNullTask(1));
    }

    @Test
    @DisplayName("Insertion of the task after termination")
    void insertionAfterTermination() {
        assertThatExceptionOfType(RejectedExecutionException.class)
            .isThrownBy(() -> tryToRunAfterTermination(1, () -> System.out.println("I'm stupid task")));
    }

    @Test
    @DisplayName("Simple pool testing")
    void simplePoolTesting() {
        final int threadCount = 10;

        var actualValues = executeSimpleTaskNTimesAndGetListOfResults(threadCount);
        List<String> expectedValues = Collections.nCopies(threadCount, "bob");

        assertThat(actualValues).containsExactlyInAnyOrderElementsOf(expectedValues);
    }

    @Test
    @DisplayName("Multithreading fibonacci testing")
    void multithreadingFibonacciTesting() throws Exception {
        final int threadsCount = 30;

        List<Integer> actualValues = calculateNFibonacciNumbersUsingThreadPool(threadsCount);
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

    private static void tryToCreateThreadPoolWithIncorrectSize(int size) {
        try (ThreadPool ignored = Task2.FixedThreadPool.create(size)) {
            LogManager.getLogger().info("No exception was thrown by incorrect size");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void tryToCreateThreadPoolWithNullTask(int size) {
        try (ThreadPool pool = Task2.FixedThreadPool.create(size)) {
            pool.execute(null);
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void tryToRunAfterTermination(int size, Runnable runnable) {
        try (ThreadPool pool = Task2.FixedThreadPool.create(size)) {
            pool.execute(runnable);
            pool.start();
            pool.close();
            pool.execute(runnable);
        } catch (RejectedExecutionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> executeSimpleTaskNTimesAndGetListOfResults(int N) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        try (ThreadPool pool = Task2.FixedThreadPool.create(N)) {
            for (int i = 0; i < N; i++) {
                pool.execute(() -> list.add("bob"));
            }
            pool.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private static List<Integer> calculateNFibonacciNumbersUsingThreadPool(int N) throws InterruptedException {
        AtomicInteger current = new AtomicInteger(1);
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        try (ThreadPool pool = Task2.FixedThreadPool.create(N)) {
            for (int i = 0; i < N; i++) {
                pool.execute(() -> {
                    int answer = fibonacci(current.incrementAndGet());
                    list.add(answer);
                });
            }
            pool.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Thread.sleep(100);
        return list;
    }
}
