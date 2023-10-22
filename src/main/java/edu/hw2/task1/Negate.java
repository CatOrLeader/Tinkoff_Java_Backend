package edu.hw2.task1;

import org.jetbrains.annotations.NotNull;

public record Negate(@NotNull Expr expression) implements Expr {
    @Override
    public double evaluate() {
        return (-1) * expression.evaluate();
    }
}
