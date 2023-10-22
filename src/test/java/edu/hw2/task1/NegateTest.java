package edu.hw2.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NegateTest {
    @Test
    @DisplayName("Correct positive value provide and output")
    void correctPositiveValueTest() {
        Expr correctValue = new Constant(25);

        double actualValue = new Negate(correctValue).evaluate();
        double expectedValue = -1 * correctValue.evaluate();

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct negative value provide and output")
    void correctNegativeValueTest() {
        Expr correctValue = new Constant(-25);

        double actualValue = new Negate(correctValue).evaluate();
        double expectedValue = -1 * correctValue.evaluate();

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
