package edu.hw7;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task1Test {
    @Test
    @DisplayName("Single thread increment")
    void singleThread() throws InterruptedException {
        AtomicInteger num = new AtomicInteger(0);
        int countOfThreads = 1;
        int finalNumber = 100;

        Task1.increment(num, countOfThreads, finalNumber);

        assertThat(num.get()).isEqualTo(finalNumber);
    }

    @Test
    @DisplayName("Divisible amount of threads")
    void divisibleAmountOfThreads() throws InterruptedException {
        AtomicInteger num = new AtomicInteger(0);
        int countOfThreads = 5;
        int finalNumber = 100;

        Task1.increment(num, countOfThreads, finalNumber);

        assertThat(num.get()).isEqualTo(finalNumber);
    }

    @Test
    @DisplayName("Non divisible amount of threads, odd number of threads")
    void nonDivisibleAmountOfThreads_odd() throws InterruptedException {
        AtomicInteger num = new AtomicInteger(0);
        int countOfThreads = 3;
        int finalNumber = 100;

        Task1.increment(num, countOfThreads, finalNumber);

        assertThat(num.get()).isEqualTo(finalNumber);
    }

    @Test
    @DisplayName("Non divisible amount of threads, even number of threads")
    void nonDivisibleAmountOfThreads_even() throws InterruptedException {
        AtomicInteger num = new AtomicInteger(0);
        int countOfThreads = 4;
        int finalNumber = 123;

        Task1.increment(num, countOfThreads, finalNumber);

        assertThat(num.get()).isEqualTo(finalNumber);
    }

    @Test
    @DisplayName("Incorrect initial and final values provided")
    void incorrectInitialAndFinalValues() throws InterruptedException {
        AtomicInteger num = new AtomicInteger(25);
        int countOfThreads = 1;
        int finalNumber = -25;

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Task1.increment(num, countOfThreads, finalNumber))
            .withMessage("Incorrect initial and final numbers provided");
    }

    @Test
    @DisplayName("Incorrect number of threads provided")
    void incorrectAmountOfThreads() throws InterruptedException {
        AtomicInteger num = new AtomicInteger(0);
        int countOfThreads = 0;
        int finalNumber = 25;

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Task1.increment(num, countOfThreads, finalNumber))
            .withMessage("Incorrect amount of threads provided");
    }
}
