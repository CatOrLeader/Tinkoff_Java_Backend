package edu.project3.types;

import org.jetbrains.annotations.NotNull;

public enum OutputFormat {
    MARKDOWN, ADOC;

    public static OutputFormat parseValue(@NotNull String value) {
        if (value.equals("adoc")) {
            return ADOC;
        } else {
            return MARKDOWN;
        }
    }
}
