package edu.hw2.task1;

import org.jetbrains.annotations.NotNull;

public record Exponent(@NotNull Expr base, double exp) implements Expr {
    @Override
    public double evaluate() {
        return Math.pow(base.evaluate(), exp);
    }
}
