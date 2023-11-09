package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {
    static Arguments[] correctSigns() {
        return new Arguments[] {
            Arguments.of("А123ВЕ777"),
            Arguments.of("О777ОО177")
        };
    }

    static Arguments[] incorrectSigns() {
        return new Arguments[] {
            Arguments.of("123АВЕ777"),
            Arguments.of("А123ВГ77"),
            Arguments.of("А123ВЕ7777")
        };
    }

    @ParameterizedTest
    @MethodSource("correctSigns")
    @DisplayName("Correct signs test")
    void correctSigns(String sign) {
        assertThat(Task5.isCorrectSign(sign)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("incorrectSigns")
    @DisplayName("Correct signs test")
    void incorrectSigns(String sign) {
        assertThat(Task5.isCorrectSign(sign)).isFalse();
    }
}
