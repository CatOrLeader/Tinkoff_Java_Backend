package edu.hw2.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MultiplicationTest {
    @Test
    @DisplayName("Correct two positive values provided")
    void correctPosValueTest() {
        Expr[] expressions = new Expr[]{new Constant(2), new Constant(2)};

        double actualValue = new Multiplication(expressions).evaluate();
        double expectedValue = 4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct one positive and one negative values provided")
    void correctPosAndNegValueTest() {
        Expr[] expressions = new Expr[]{new Constant(2), new Negate(new Constant(2))};

        double actualValue = new Multiplication(expressions).evaluate();
        double expectedValue = -4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct two neg values provided")
    void correctNegValueTest() {
        Expr[] expressions = new Expr[]{new Constant(-2), new Constant(-2)};

        double actualValue = new Multiplication(expressions).evaluate();
        double expectedValue = 4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("More than two arguments provided")
    void correctPositiveValueTest() {
        Expr[] expressions = new Expr[]{
            new Constant(2),
            new Negate(new Constant(-2)),
            new Addition(new Constant(3), new Constant(5)),
            new Exponent(new Constant(3), 2)
        };

        double actualValue = new Multiplication(expressions).evaluate();
        double expectedValue = 288;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect number of expressions provided")
    void incorrectNumberOfExpressions() {
        Expr[] expressions = new Expr[]{};

        double actualValue = new Multiplication(expressions).evaluate();
        double expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
