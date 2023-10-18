package edu.hw2.task1;

import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

public record Addition(@NotNull Expr... expressions) implements Expr {
    /**
     * This function evaluate value for the addition of the several arguments (not bounded). If
     * there are no expressions provided (null, zero-length array), method log it and return 0
     * @return addition value; 0 - if zero-length array, null provided
     */
    @Override
    public double evaluate() {
        if (expressions.length == 0) {
            LogManager.getLogger().info("Incorrect number of arguments");
        }
        double sum = 0;
        for (Expr expression : expressions) {
            sum += expression.evaluate();
        }
        return sum;
    }
}
