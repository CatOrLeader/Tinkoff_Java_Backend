package edu.hw2.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExpressionTest {
    @Test
    @DisplayName("Expression in the terms of the written records, evaluated")
    void expressionSolution1() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, 2);
        var res = new Addition(exp, new Constant(1));

        double actualValue = res.evaluate();
        double expectedValue = 37;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Expression in the terms of the written records, evaluated")
    void expressionSolution2() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four, negOne);
        var exp = new Exponent(sumTwoFour, 2);
        var res = new Addition(exp, new Constant(1));

        double actualValue = res.evaluate();
        double expectedValue = 26;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
