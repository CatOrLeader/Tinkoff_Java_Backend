package edu.hw5;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class Task4 {
    private Task4() {
    }

    private static final String REGEXP_KEYWORDS = "$^*|";
    private static final String SPECIAL_SYMBOLS = "~!@#$%^&*|\0";
    private static final Pattern PATTERN = definePattern();

    private static Pattern definePattern() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SPECIAL_SYMBOLS.length(); i++) {
            StringBuilder exclusive = new StringBuilder();
            exclusive.append("(^(?!.*(");
            for (int j = 0; j < SPECIAL_SYMBOLS.length(); j++) {
                if (i == j) {
                    continue;
                }

                if (REGEXP_KEYWORDS.contains(String.valueOf(SPECIAL_SYMBOLS.charAt(j)))) {
                    exclusive.append("\\");
                }

                if (j == SPECIAL_SYMBOLS.length() - 1) {
                    exclusive.append(SPECIAL_SYMBOLS.charAt(j));
                    break;
                }

                exclusive.append(SPECIAL_SYMBOLS.charAt(j)).append("|");
            }
            exclusive.append(")).*");

            sb.append(exclusive).append("\\").append(SPECIAL_SYMBOLS.charAt(i)).append(exclusive.substring(2))
                .append(")");

            if (i != SPECIAL_SYMBOLS.length() - 1) {
                sb.append("|");
            }
        }

        return Pattern.compile(sb.toString());
    }

    public static boolean hasContainsSpecialSymbol(@NotNull String password) {
        return PATTERN.matcher(password).matches();
    }
}
