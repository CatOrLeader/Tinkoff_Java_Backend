package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task1.minutesToSeconds;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Correct Input 1")
    void correctInput1() {
        String input = "01:00";

        long actualValue = minutesToSeconds(input);
        long expectedValue = 60;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct Input 2")
    void correctInput2() {
        String input = "13:56";

        long actualValue = minutesToSeconds(input);
        long expectedValue = 836;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect Input 1")
    void incorrectInput1() {
        String input = "10:60";

        long actualValue = minutesToSeconds(input);
        long expectedValue = -1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect Input 2")
    void incorrectInput2() {
        String input = "-10:60";

        long actualValue = minutesToSeconds(input);
        long expectedValue = -1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Letters input 1")
    void lettersInput1() {
        String input = "abs:22";

        long actualValue = minutesToSeconds(input);
        long expectedValue = -1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Letters input 2")
    void lettersInput2() {
        String input = "3:abs";

        long actualValue = minutesToSeconds(input);
        long expectedValue = -1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Letters input 3")
    void lettersInput3() {
        String input = "lol:abs";

        long actualValue = minutesToSeconds(input);
        long expectedValue = -1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("No colon")
    void noColon() {
        String input = "1324";

        long actualValue = minutesToSeconds(input);
        long expectedValue = -1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
