package edu.hw3;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;

public final class Task2 {
    private static final String INCORRECT_INPUT_CAUSE = "incorrect balance between open and close parenthesis";
    private static final Stack<Character> RPE_STACK = new Stack<>();

    private Task2() {
    }

    @NotNull
    public static ArrayList<String> clusterize(@NotNull String string) {
        ArrayList<String> clusters = new ArrayList<>();
        StringBuilder clusterBuilder = new StringBuilder();

        for (char ch : string.toCharArray()) {
            if (isOpenParenthesis(ch)) {
                RPE_STACK.push(ch);
                clusterBuilder.append(ch);
            } else if (isCloseParenthesis(ch)) {
                if (!isCorrect()) {
                    throw new InputMismatchException(INCORRECT_INPUT_CAUSE);
                }

                clusterBuilder.append(ch);
                RPE_STACK.pop();
                if (isBalanced()) {
                    clusters.add(clusterBuilder.toString());
                    clusterBuilder = new StringBuilder();
                }
            }
        }

        if (!RPE_STACK.isEmpty()) {
            throw new InputMismatchException(INCORRECT_INPUT_CAUSE);
        }

        return clusters;
    }

    private static boolean isBalanced() {
        return RPE_STACK.isEmpty();
    }

    private static boolean isCorrect() {
        return !RPE_STACK.isEmpty();
    }

    private static boolean isOpenParenthesis(char ch) {
        return ch == '(';
    }

    private static boolean isCloseParenthesis(char ch) {
        return ch == ')';
    }
}
