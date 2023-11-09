package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

public class Task8Test {
    @Test
    @DisplayName("Even length string, pattern one")
    void evenLength_patternOne() {
        String input = "1010";

        assertThat(Task8.isPatternOne(input)).isFalse();
    }

    @Test
    @DisplayName("Empty string, pattern one")
    void emptyString_patternOne() {
        String input = "";

        assertThat(Task8.isPatternOne(input)).isFalse();
    }

    @Test
    @DisplayName("Odd length string, pattern one")
    void oddLength_patternOne() {
        String input = "10101";

        assertThat(Task8.isPatternOne(input)).isTrue();
    }

    @Test
    @DisplayName("Odd length string, starting with 0, pattern two")
    void oddLength_startingZero_patternTwo() {
        String input = "01011";

        assertThat(Task8.isPatternTwo(input)).isTrue();
    }

    @Test
    @DisplayName("Odd length string, starting with 1, pattern two")
    void oddLength_startingOne_patternTwo() {
        String input = "11011";

        assertThat(Task8.isPatternTwo(input)).isFalse();
    }

    @Test
    @DisplayName("Even length string, starting with 0, pattern two")
    void evenLength_startingZero_patternTwo() {
        String input = "0101";

        assertThat(Task8.isPatternTwo(input)).isFalse();
    }

    @Test
    @DisplayName("Even length string, starting with 1, pattern two")
    void evenLength_startingOne_patternTwo() {
        String input = "1101";

        assertThat(Task8.isPatternTwo(input)).isTrue();
    }

    @Test
    @DisplayName("Empty string, pattern two")
    void emptyString_patternTwo() {
        String input = "";

        assertThat(Task8.isPatternTwo(input)).isFalse();
    }

    @Test
    @DisplayName("Empty string, pattern three")
    void emptyString_patternThree() {
        String input = "";

        assertThat(Task8.isPatternThree(input)).isTrue();
    }

    @Test
    @DisplayName("Number of zeroes not divided by three, pattern three")
    void numberNotDivided_patternThree() {
        String input = "1110110100111";

        assertThat(Task8.isPatternThree(input)).isFalse();
    }

    @Test
    @DisplayName("Number of zeroes divided by three, pattern three")
    void numberDivided_patternThree() {
        String input = "11101111100111";

        assertThat(Task8.isPatternThree(input)).isTrue();
    }

    @Test
    @DisplayName("Empty string, pattern four")
    void emptyString_patternFour() {
        String input = "";

        assertThat(Task8.isPatternFour(input)).isTrue();
    }

    @Test
    @DisplayName("Incorrect string 11, pattern four")
    void incorrectStringTwoSymbols_patternFour() {
        String input = "11";

        assertThat(Task8.isPatternFour(input)).isFalse();
    }

    @Test
    @DisplayName("Incorrect string 111, pattern four")
    void incorrectStringThreeSymbols_patternFour() {
        String input = "111";

        assertThat(Task8.isPatternFour(input)).isFalse();
    }

    @Test
    @DisplayName("Correct string, pattern four")
    void correctString_patternFour() {
        String input = "111100101";

        assertThat(Task8.isPatternFour(input)).isTrue();
    }

    @Test
    @DisplayName("Empty string, pattern five")
    void emptyString_patternFive() {
        String input = "";

        assertThat(Task8.isPatternFive(input)).isTrue();
    }

    @Test
    @DisplayName("Incorrect string, pattern five")
    void incorrectString_patternFive() {
        String input = "000000";

        assertThat(Task8.isPatternFive(input)).isFalse();
    }

    @Test
    @DisplayName("Correct string, pattern five")
    void correctString_patternFive() {
        String input = "10101110";

        assertThat(Task8.isPatternFive(input)).isTrue();
    }

    @Test
    @DisplayName("Correct string one symbol, pattern five")
    void correctString_oneSymbol_patternFive() {
        String input = "1";

        assertThat(Task8.isPatternFive(input)).isTrue();
    }

    @Test
    @DisplayName("Empty string, pattern seven")
    void emptyString_patternSeven() {
        String input = "";

        assertThat(Task8.isPatternSeven(input)).isTrue();
    }

    @Test
    @DisplayName("Incorrect string, pattern seven")
    void incorrectString_patternSeven() {
        String input = "110001110101";

        assertThat(Task8.isPatternSeven(input)).isFalse();
    }

    @Test
    @DisplayName("Correct string, pattern seven")
    void correctString_patternSeven() {
        String input = "0000010000001010000";

        assertThat(Task8.isPatternSeven(input)).isTrue();
    }
}
