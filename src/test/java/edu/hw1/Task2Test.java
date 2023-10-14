package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task2.countDigits;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Correct Input 1")
    void correctInput1() {
        long input = 4666;

        int actualCount = countDigits(input);
        int expectedCount = 4;

        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @Test
    @DisplayName("Correct Input 2")
    void correctInput2() {
        long input = 544;

        int actualCount = countDigits(input);
        int expectedCount = 3;

        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @Test
    @DisplayName("Correct Input 3")
    void correctInput3() {
        long input = 0;

        int actualCount = countDigits(input);
        int expectedCount = 1;

        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @Test
    @DisplayName("Negative Input")
    void negativeInput() {
        long input = -2023;

        int actualCount = countDigits(input);
        int expectedCount = 4;

        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @Test
    @DisplayName("Negative Zero")
    void negativeZero() {
        long input = -0;

        int actualCount = countDigits(input);
        int expectedCount = 1;

        assertThat(actualCount).isEqualTo(expectedCount);
    }
}
