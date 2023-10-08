package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw1.Task5.isPalindromeDescendant;

public class Task5Test {
    @Test
    @DisplayName("Even length number 1")
    void evenLengthNum1() {
        long num = 11211230;

        boolean actualValue = isPalindromeDescendant(num);
        boolean expectedValue = true;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Even length number 2")
    void evenLengthNum2() {
        long num = 13001120;

        boolean actualValue = isPalindromeDescendant(num);
        boolean expectedValue = true;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Even length number 3")
    void evenLengthNum3() {
        long num = 23336014;

        boolean actualValue = isPalindromeDescendant(num);
        boolean expectedValue = true;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Even length number 4")
    void evenLengthNum4() {
        long num = 23336015;

        boolean actualValue = isPalindromeDescendant(num);
        boolean expectedValue = false;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Odd length number 1")
    void oddLengthNum1() {
        long num = 2187032;

        boolean actualValue = isPalindromeDescendant(num);
        boolean expectedValue = false;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Odd length number 2")
    void oddLengthNum2() {
        long num = 2187031;

        boolean actualValue = isPalindromeDescendant(num);
        boolean expectedValue = true;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("One character number")
    void oneCharacterNum() {
        long num = 1;

        boolean actualValue = isPalindromeDescendant(num);
        boolean expectedValue = false;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Negative even characters number")
    void negativeEvenNum() {
        long num = -11211230;

        boolean actualValue = isPalindromeDescendant(num);
        boolean expectedValue = true;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Negative odd characters number")
    void negativeOddNum() {
        long num = -3187032;

        boolean actualValue = isPalindromeDescendant(num);
        boolean expectedValue = true;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
