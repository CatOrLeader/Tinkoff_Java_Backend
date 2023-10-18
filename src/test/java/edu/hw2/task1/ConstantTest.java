package edu.hw2.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import static org.assertj.core.api.Assertions.assertThat;

public class ConstantTest {
    @Test
    @DisplayName("Correct positive value provide and output")
    void correctPositiveValueTest() {
        double correctValue = 25;

        double actualValue = new Constant(correctValue).evaluate();
        double expectedValue = correctValue;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct negative value provide and output")
    void correctNegativeValueTest() {
        double correctValue = -25;

        double actualValue = new Constant(correctValue).evaluate();
        double expectedValue = correctValue;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
