package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jetbrains.annotations.NotNull;

public final class Task1 {
    private Task1() {
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd, HH:mm");
    private static final int DELIMITER_POSITION = 18;

    public static @NotNull Duration averageTime(@NotNull String... strings) {
        if (strings.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        long seconds = 0;
        for (String str : strings) {
            String startTimeStr = str.substring(0, DELIMITER_POSITION).trim();
            String endTimeStr = str.substring(DELIMITER_POSITION + 1).trim();

            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, FORMATTER);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, FORMATTER);

            seconds += Duration.between(startTime, endTime).getSeconds();
        }

        return Duration.ofSeconds((long) ((double) seconds / strings.length));
    }
}
