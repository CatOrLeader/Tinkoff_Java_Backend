package edu.hw3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Single digit number")
    void singleDigitNumber() {
        int number = 2;

        String actualValue = Task4.convertToRoman(number);
        String expectedValue = "II";

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Doubly digits number")
    void doublyDigitNumber() {
        int number = 16;

        String actualValue = Task4.convertToRoman(number);
        String expectedValue = "XVI";

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Triply digits number")
    void triplyDigitNumber() {
        int number = 123;

        String actualValue = Task4.convertToRoman(number);
        String expectedValue = "CXXIII";

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Four digits number")
    void fourDigitNumber() {
        int number = 4000;

        String actualValue = Task4.convertToRoman(number);
        String expectedValue = "MMMM";

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect number from below provided")
    void incorrectNumber_fromBelow() {
        int number = -25;

        Assertions.assertThrows(IllegalArgumentException.class, () -> Task4.convertToRoman(number));
    }

    @Test
    @DisplayName("Incorrect number from above provided")
    void incorrectNumber_fromAbove() {
        int number = 4500;

        Assertions.assertThrows(IllegalArgumentException.class, () -> Task4.convertToRoman(number));
    }
}
