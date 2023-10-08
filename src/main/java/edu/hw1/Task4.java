package edu.hw1;

import org.jetbrains.annotations.NotNull;

public final class Task4 {
    private Task4() { }

    /**
     * Method fix the string and return the new String() object
     * @param incorrectStr incorrectly written string
     * @return new object with the correct grammar of the string
     */
    public static String fixString(@NotNull String incorrectStr) {
        StringBuilder sb = new StringBuilder(incorrectStr);
        for (int i = 0; i < incorrectStr.length() - 1; i += 2) {
            sb.setCharAt(i, incorrectStr.charAt(i + 1));
            sb.setCharAt(i + 1, incorrectStr.charAt(i));
        }
        return sb.toString();
    }
}
