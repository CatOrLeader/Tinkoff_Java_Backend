package edu.hw2.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExponentTest {
    @Test
    @DisplayName("Positive value and positive exponent")
    void posValuePosExponent() {
        Expr constant = new Constant(2);
        int exp = 2;

        double actualValue = new Exponent(constant, exp).evaluate();
        double expectedValue = 4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Positive value and negative exponent")
    void posValueNegExponent() {
        Expr constant = new Constant(2);
        int exp = -2;

        double actualValue = new Exponent(constant, exp).evaluate();
        double expectedValue = -4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
