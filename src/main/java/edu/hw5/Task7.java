package edu.hw5;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class Task7 {
    private Task7() {}

    private static final Pattern PATTERN_1 = Pattern.compile("^[01]{2}0[01]*");
    private static final Pattern PATTERN_2 = Pattern.compile("(0[01]*0)|(1[01]*1)");
    private static final Pattern PATTERN_3 = Pattern.compile("([01])|([01]{2})|([01]{3})");

    public static boolean isPatternOne(@NotNull String string) {
        return PATTERN_1.matcher(string).matches();
    }

    public static boolean isPatternTwo(@NotNull String string) {
        return PATTERN_2.matcher(string).matches();
    }

    public static boolean isPatternThree(@NotNull String string) {
        return PATTERN_3.matcher(string).matches();
    }
}
