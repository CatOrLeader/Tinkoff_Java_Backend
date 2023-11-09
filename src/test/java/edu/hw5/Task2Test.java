package edu.hw5;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Nonpositive year provided")
    void nonpositiveYear() {
        int year = -1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> Task2.getAllFridaysFromYear(year));
    }

    @Test
    @DisplayName("Tinkoff test 1")
    void tinkoff1() {
        int year = 1925;

        List<LocalDate> actualValue = Task2.getAllFridaysFromYear(year);
        List<LocalDate> expectedValue = List.of(
            LocalDate.of(1925, 2, 13),
            LocalDate.of(1925, 3, 13),
            LocalDate.of(1925, 11, 13)
        );

        assertThat(actualValue).containsExactlyElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Tinkoff test 2")
    void tinkoff2() {
        int year = 2024;

        List<LocalDate> actualValue = Task2.getAllFridaysFromYear(year);
        List<LocalDate> expectedValue = List.of(
            LocalDate.of(2024, 9, 13),
            LocalDate.of(2024, 12, 13)
        );

        assertThat(actualValue).containsExactlyElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Tinkoff test 1, closest next friday")
    void tinkoff1_closestFriday() {
        LocalDate current = LocalDate.of(1925, 3, 13);

        LocalDate actualValue = Task2.getNextFridayThirteen(current);
        LocalDate expectedValue = LocalDate.of(1925, 11, 13);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Tinkoff test 2, closest next friday")
    void tinkoff2_closestFriday() {
        LocalDate current = LocalDate.of(2024, 9, 13);

        LocalDate actualValue = Task2.getNextFridayThirteen(current);
        LocalDate expectedValue = LocalDate.of(2024, 12, 13);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Closest next friday, gap year")
    void closestFriday_yearGap() {
        LocalDate current = LocalDate.of(2024, 12, 13);

        LocalDate actualValue = Task2.getNextFridayThirteen(current);
        LocalDate expectedValue = LocalDate.of(2025, 6, 13);

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
