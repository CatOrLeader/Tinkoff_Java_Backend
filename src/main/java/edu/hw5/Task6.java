package edu.hw5;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class Task6 {
    private Task6() {}

    public static boolean isSubstring(@NotNull String string, @NotNull String maybeSubstring) {
        return Pattern.compile(maybeSubstring).matcher(string).find();
    }
}
