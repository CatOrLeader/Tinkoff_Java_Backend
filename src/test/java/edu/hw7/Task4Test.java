package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task4Test {
    @Test
    @DisplayName("Single thread evaluation n < 1")
    void singleThread_IncorrectN() {
        int n = 0;

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Task4.calculatePiSingleThread(n))
            .withMessage("Incorrect n provided");
    }

    @Test
    @DisplayName("Multi thread evaluation n < 1")
    void multiThread_IncorrectN() {
        int n = 0;

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Task4.calculatePiMultiThreads(n, 5))
            .withMessage("Incorrect n provided");
    }

    @Test
    @DisplayName("Single thread evaluation n = 10_000")
    void singleThread_1() {
        int n = 10_000;

        double actualValue = Task4.calculatePiSingleThread(n);
        double expectedLowestBorder = 3.08;
        double expectedHighestBorder = 3.2;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }

    @Test
    @DisplayName("Multi thread evaluation n = 10_000")
    void multiThread_1() throws InterruptedException {
        int n = 10_000;
        int countOfThreads = 4;

        double actualValue = Task4.calculatePiMultiThreads(n, countOfThreads);
        double expectedLowestBorder = 3.08;
        double expectedHighestBorder = 3.2;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }

    @Test
    @DisplayName("Single thread evaluation n = 100_000")
    void singleThread_2() {
        int n = 100_000;

        double actualValue = Task4.calculatePiSingleThread(n);
        double expectedLowestBorder = 3.13;
        double expectedHighestBorder = 3.15;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }

    @Test
    @DisplayName("Multi thread evaluation n = 100_000")
    void multiThread_2() throws InterruptedException {
        int n = 100_000;
        int countOfThreads = 4;

        double actualValue = Task4.calculatePiMultiThreads(n, countOfThreads);
        double expectedLowestBorder = 3.13;
        double expectedHighestBorder = 3.153;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }

    @Test
    @DisplayName("Single thread evaluation n = 1_000_000")
    void singleThread_3() {
        int n = 1_000_000;

        double actualValue = Task4.calculatePiSingleThread(n);
        double expectedLowestBorder = 3.135;
        double expectedHighestBorder = 3.145;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }

    @Test
    @DisplayName("Multi thread evaluation n = 1_000_000")
    void multiThread_3() throws InterruptedException {
        int n = 1_000_000;
        int countOfThreads = 4;

        double actualValue = Task4.calculatePiMultiThreads(n, countOfThreads);
        double expectedLowestBorder = 3.135;
        double expectedHighestBorder = 3.145;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }

    @Test
    @DisplayName("Single thread evaluation n = 10_000_000")
    void singleThread_4() {
        int n = 10_000_000;

        double actualValue = Task4.calculatePiSingleThread(n);
        double expectedLowestBorder = 3.14;
        double expectedHighestBorder = 3.143;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }

    @Test
    @DisplayName("Multi thread evaluation n = 10_000_000")
    void multiThread_4() throws InterruptedException {
        int n = 10_000_000;
        int countOfThreads = 4;

        double actualValue = Task4.calculatePiMultiThreads(n, countOfThreads);
        double expectedLowestBorder = 3.14;
        double expectedHighestBorder = 3.143;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }

    @Test
    @DisplayName("Single thread evaluation n = 100_000_000")
    void singleThread_5() {
        int n = 100_000_000;

        double actualValue = Task4.calculatePiSingleThread(n);
        double expectedLowestBorder = 3.14;
        double expectedHighestBorder = 3.143;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }

    @Test
    @DisplayName("Multi thread evaluation n = 100_000_000")
    void multiThread_5() throws InterruptedException {
        int n = 100_000_000;
        int countOfThreads = 4;

        double actualValue = Task4.calculatePiMultiThreads(n, countOfThreads);
        double expectedLowestBorder = 3.14;
        double expectedHighestBorder = 3.143;

        assertThat(actualValue).isBetween(expectedLowestBorder, expectedHighestBorder);
    }
}
