package edu.hw5;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class Task5 {
    private Task5() {
    }

    private static final Pattern PATTERN = Pattern.compile(
        "([А-Я]\\d{3}[А-Я]{2}\\d{3})"
    );

    public static boolean isCorrectSign(@NotNull String sign) {
        return PATTERN.matcher(sign).matches();
    }
}
