package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task2Test {
    @Test
    @DisplayName("Incorrect value provided")
    void incorrectValue() {
        int n = -1;

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Task2.factorial(n))
            .withMessage("Incorrect n provided");
    }

    @Test
    @DisplayName("Correct value provided, even number")
    void correctValue_evenNumber() {
        int n = 6;

        long actualValue = Task2.factorial(n);
        long expectedValue = 720;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct value provided, odd number")
    void correctValue_oddNumber() {
        int n = 13;

        long actualValue = Task2.factorial(n);
        long expectedValue = 6227020800L;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
