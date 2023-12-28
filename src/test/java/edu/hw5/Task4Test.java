package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    static Arguments[] specialSymbols () {
        return new Arguments[] {
            Arguments.of("~"),
            Arguments.of("!"),
            Arguments.of("@"),
            Arguments.of("#"),
            Arguments.of("$"),
            Arguments.of("%"),
            Arguments.of("^"),
            Arguments.of("&"),
            Arguments.of("*"),
            Arguments.of("|")
        };
    }
    private static final String specialSymbolsReversed = "|*&^%$#@!~";

    @Test
    @DisplayName("No special symbols provided")
    void noSpecialSymbols() {
        String inputString = "password";

        boolean actualValue = Task4.hasContainsSpecialSymbol(inputString);

        assertThat(actualValue).isFalse();
    }

    @Test
    @DisplayName("Empty string provided")
    void emptyString() {
        String inputString = "";

        boolean actualValue = Task4.hasContainsSpecialSymbol(inputString);

        assertThat(actualValue).isFalse();
    }

    @ParameterizedTest
    @MethodSource("specialSymbols")
    @DisplayName("Exactly one special symbol provided")
    void exactlyOneSpecialSymbol(String specialSymbol) {
        boolean actualValue = Task4.hasContainsSpecialSymbol(specialSymbol);

        assertThat(actualValue).isTrue();
    }

    @ParameterizedTest
    @MethodSource("specialSymbols")
    @DisplayName("One special symbol with another characters provided")
    void OneSpecialSymbolWithAnotherWords(String specialSymbol) {
        String input = "pass" + specialSymbol + "word";

        boolean actualValue = Task4.hasContainsSpecialSymbol(input);

        assertThat(actualValue).isTrue();
    }

    @ParameterizedTest
    @MethodSource("specialSymbols")
    @DisplayName("Two same special symbols with another characters provided")
    void TwoSameSpecialSymbolsWithAnotherWords(String specialSymbol) {
        String input = "pass" + specialSymbol + "word" + specialSymbol;

        boolean actualValue = Task4.hasContainsSpecialSymbol(input);

        assertThat(actualValue).isTrue();
    }

    @ParameterizedTest
    @MethodSource("specialSymbols")
    @DisplayName("Two different special symbols with another characters provided")
    void TwoDifferentSpecialSymbolsWithAnotherWords(String specialSymbol) {
        String input = "pass" + specialSymbol + "word";

        for (char ch : specialSymbolsReversed.toCharArray()) {
            if (specialSymbol.toCharArray()[0] == ch) {
                continue;
            }
            assertThat(Task4.hasContainsSpecialSymbol(input + ch)).isFalse();
        }
    }
}
