package edu.hw5;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class Task8 {
    private Task8() {
    }

    private static final Pattern PATTERN_1 = Pattern.compile("[01](00|01|10|11)*");
    private static final Pattern PATTERN_2 = Pattern.compile("((0)|(1[01]))(00|01|10|11)*");
    private static final Pattern PATTERN_3 = Pattern.compile("1*(1*01*01*01*)*1*");
    private static final Pattern PATTERN_4 = Pattern.compile(
        "(110+[01]*)|(111[01]+)|(0+[01]*)|0*"
    );
    private static final Pattern PATTERN_5 = Pattern.compile("(1)|(1[01])*|(1[01]1)*");
    private static final Pattern PATTERN_7 = Pattern.compile("(0*10+)*");

    public static boolean isPatternOne(@NotNull String string) {
        return PATTERN_1.matcher(string).matches();
    }

    public static boolean isPatternTwo(@NotNull String string) {
        return PATTERN_2.matcher(string).matches();
    }

    public static boolean isPatternThree(@NotNull String string) {
        return PATTERN_3.matcher(string).matches();
    }

    public static boolean isPatternFour(@NotNull String string) {
        return PATTERN_4.matcher(string).matches();
    }

    public static boolean isPatternFive(@NotNull String string) {
        return PATTERN_5.matcher(string).matches();
    }

    public static boolean isPatternSeven(@NotNull String string) {
        return PATTERN_7.matcher(string).matches();
    }
}
