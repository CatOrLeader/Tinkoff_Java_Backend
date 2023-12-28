package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {
    private static final String incorrectString = "21x2341";
    private static final String correctString = "achfdbaabgabcaabg";
    static Arguments[] getSubstrings() {
        return new Arguments[] {
            Arguments.of("abc"),
            Arguments.of("baa"),
            Arguments.of("bc"),
            Arguments.of("g"),
            Arguments.of("achfdbaabgabcaabg")
        };
    }

    @ParameterizedTest
    @MethodSource("getSubstrings")
    @DisplayName("Correct substring provided")
    void correctSubstring(String substring) {
        assertThat(Task6.isSubstring(correctString, substring)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getSubstrings")
    @DisplayName("Correct substring provided")
    void incorrectSubstring(String substring) {
        assertThat(Task6.isSubstring(incorrectString, substring)).isFalse();
    }
}
