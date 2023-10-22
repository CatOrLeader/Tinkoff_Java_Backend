package edu.hw2.task1;

import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

public record Multiplication(@NotNull Expr... expressions) implements Expr {
    /**
     * This function evaluate value for the multiplication of the several arguments (not bounded). If
     * there are no expressions provided (null, zero-length array), method log it and return 0
     * @return multiplication value; 0 - if zero-length array, null provided
     */
    @Override
    public double evaluate() {
        if (expressions.length == 0) {
            LogManager.getLogger().info("Incorrect number of arguments");
            return 0;
        }
        double multiplication = 1;
        for (Expr expression : expressions) {
            multiplication *= expression.evaluate();
        }
        return multiplication;
    }
}
