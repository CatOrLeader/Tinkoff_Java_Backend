package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

public class Task7Test {
    @Test
    @DisplayName("Less than 3 symbols, pattern one")
    void lessThanThreeSymbols_patternOne() {
        String input = "0";

        assertThat(Task7.isPatternOne(input)).isFalse();
    }

    @Test
    @DisplayName("No zero on third place, pattern one")
    void noZeroOnThirdPlace_patternOne() {
        String input = "0010";

        assertThat(Task7.isPatternOne(input)).isFalse();
    }

    @Test
    @DisplayName("Correct string, pattern one")
    void correctString_patternOne() {
        String input = "11010100101";

        assertThat(Task7.isPatternOne(input)).isTrue();
    }

    @Test
    @DisplayName("Empty string, pattern one")
    void emptyString_patternOne() {
        String input = "";

        assertThat(Task7.isPatternOne(input)).isFalse();
    }

    @Test
    @DisplayName("Different symbols, pattern two")
    void differentSymbolsOnEnds_patternTwo() {
        String input = "1000";

        assertThat(Task7.isPatternTwo(input)).isFalse();
    }

    @Test
    @DisplayName("Empty string, pattern two")
    void emptyString_patternTwo() {
        String input = "";

        assertThat(Task7.isPatternTwo(input)).isFalse();
    }

    @Test
    @DisplayName("Two same symbols length, pattern two")
    void onlyTwoSymbols_patternTwo() {
        String input = "11";

        assertThat(Task7.isPatternTwo(input)).isTrue();
    }

    @Test
    @DisplayName("Correct string, pattern two")
    void correctString_patternTwo() {
        String input = "10001001101";

        assertThat(Task7.isPatternTwo(input)).isTrue();
    }

    @Test
    @DisplayName("Empty string, pattern three")
    void emptyString_patternThree() {
        String input = "";

        assertThat(Task7.isPatternThree(input)).isFalse();
    }

    @Test
    @DisplayName("More than three symbols string, pattern three")
    void moreThanThreeSymbolsLength_patternThree() {
        String input = "0000001111111110000000";

        assertThat(Task7.isPatternThree(input)).isFalse();
    }

    @Test
    @DisplayName("Correct string, pattern three")
    void correctString_patternThree() {
        String input = "01";

        assertThat(Task7.isPatternThree(input)).isTrue();
    }
}
