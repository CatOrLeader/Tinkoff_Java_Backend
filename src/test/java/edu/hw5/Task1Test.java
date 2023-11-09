package edu.hw5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.zip.DataFormatException;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Tinkoff test")
    void tinkoffTest() {
        String[] strings = new String[] {
            "2022-03-12, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20"
        };

        Duration actualValue = Task1.averageTime(strings);
        Duration expectedValue = Duration.ofHours(3).plusMinutes(40);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("One year test")
    void oneYearTest() {
        String[] strings = new String[] {
            "2021-03-12, 20:20 - 2022-03-12, 23:50"
        };

        Duration actualValue = Task1.averageTime(strings);
        Duration expectedValue = Duration.ofDays(365).plusHours(3).plusMinutes(30);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect format provided")
    void incorrectFormatProvided() {
        String[] strings = new String[] {
            "2022-03-12, 20:20 ---- 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20"
        };

        Assertions.assertThrows(DateTimeParseException.class, () -> Task1.averageTime(strings));
    }

    @Test
    @DisplayName("Empty array provided")
    void emptyArrayProvided() {
        String[] strings = new String[]{};

        Assertions.assertThrows(IllegalArgumentException.class, () -> Task1.averageTime(strings));
    }
}
