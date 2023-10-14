package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task4.fixString;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Even length string")
    void evenLengthString() {
        String input = "123456";

        String actualString = fixString(input);
        String expectedString = "214365";

        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    @DisplayName("Odd length string")
    void oddLengthString() {
        String input = "badce";

        String actualString = fixString(input);
        String expectedString = "abcde";

        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    @DisplayName("String with spaces")
    void stringWithSpaces() {
        String input = "hTsii  s aimex dpus rtni.g";

        String actualString = fixString(input);
        String expectedString = "This is a mixed up string.";

        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    @DisplayName("Single characters string")
    void singleCharactersString() {
        String input = "A";

        String actualString = fixString(input);
        String expectedString = "A";

        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    @DisplayName("Zero characters string")
    void zeroCharactersString() {
        String input = "";

        String actualString = fixString(input);
        String expectedString = "";

        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    @DisplayName("Task example string")
    void taskExampleString() {
        String input = "оПомигети псаривьтс ртко!и";

        String actualString = fixString(input);
        String expectedString = "Помогите исправить строки!";

        assertThat(actualString).isEqualTo(expectedString);
    }
}
