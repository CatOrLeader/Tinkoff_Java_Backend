package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class Task2 {
    private Task2() {
    }

    private static final int FRIDAY_NUMBER = 13;
    private static final TemporalAdjuster NEXT_FRIDAY = TemporalAdjusters.next(DayOfWeek.FRIDAY);

    public static @NotNull List<LocalDate> getAllFridaysFromYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Incorrect year provided");
        }

        List<LocalDate> fridays = new ArrayList<>();
        LocalDate current = LocalDate.of(year, 1, 1);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        while (current.getYear() == year) {
            current = current.with(NEXT_FRIDAY);
            if (isFridayThirteen(current)) {
                fridays.add(current);
            }
        }

        return fridays;
    }

    public static @NotNull LocalDate getNextFridayThirteen(@NotNull LocalDate current) {
        LocalDate temp = current.with(NEXT_FRIDAY);
        do {
            temp = temp.with(NEXT_FRIDAY);
        } while (!isFridayThirteen(temp));

        return temp;
    }

    private static boolean isFridayThirteen(LocalDate date) {
        return date.getDayOfMonth() == FRIDAY_NUMBER && date.getDayOfWeek().equals(DayOfWeek.FRIDAY);
    }
}
