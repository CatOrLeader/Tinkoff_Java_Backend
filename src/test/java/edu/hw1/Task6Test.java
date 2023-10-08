package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw1.Task6.countK;

public class Task6Test {
    @Test
    @DisplayName("Correct Test 1")
    void correct1() {
        int num = 3524;

        int actualValue = countK(num);
        int expectedValue = 3;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct Test 2")
    void correct2() {
        int num = 6621;

        int actualValue = countK(num);
        int expectedValue = 5;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct Test 3")
    void correct3() {
        int num = 6554;

        int actualValue = countK(num);
        int expectedValue = 4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct Test 4")
    void correct4() {
        int num = 1234;

        int actualValue = countK(num);
        int expectedValue = 3;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Number lower than 1000")
    void lowerThanBound() {
        int num = 500;

        int actualValue = countK(num);
        int expectedValue = -1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Number greater than 10000")
    void greaterThanBound() {
        int num = 15000;

        int actualValue = countK(num);
        int expectedValue = -1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("All digits are the same")
    void sameDigits() {
        int num = 1111;

        int actualValue = countK(num);
        int expectedValue = -1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
