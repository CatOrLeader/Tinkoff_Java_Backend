package edu.hw2.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdditionTest {
    @Test
    @DisplayName("Correct two positive values provided")
    void correctPosValueTest() {
        Expr[] expressions = new Expr[]{new Constant(2), new Constant(2)};

        double actualValue = new Addition(expressions).evaluate();
        double expectedValue = 4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct one positive and one negative values provided")
    void correctPosAndNegValueTest() {
        Expr[] expressions = new Expr[]{new Constant(2), new Negate(new Constant(2))};

        double actualValue = new Addition(expressions).evaluate();
        double expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct two neg values provided")
    void correctNegValueTest() {
        Expr[] expressions = new Expr[]{new Constant(-2), new Constant(-2)};

        double actualValue = new Addition(expressions).evaluate();
        double expectedValue = -4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("More than two arguments provided")
    void correctPositiveValueTest() {
        Expr[] expressions = new Expr[]{
            new Constant(2),
            new Negate(new Constant(-2)),
            new Multiplication(new Constant(9999), new Constant(0)),
            new Exponent(new Constant(3), 2)
        };

        double actualValue = new Addition(expressions).evaluate();
        double expectedValue = 13;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect number of expressions provided")
    void incorrectNumberOfExpressions() {
        Expr[] expressions = new Expr[]{};

        double actualValue = new Addition(expressions).evaluate();
        double expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
