package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Iso local date format")
    void isoLocalDateFormat() {
        String inputString = "2020-10-10";

        LocalDate actualValue = Task3.parseData(inputString).get();
        LocalDate expectedValue = LocalDate.of(2020, 10, 10);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Iso local date format, one digit day")
    void isoLocalDateFormat_oneDigitDay() {
        String inputString = "2020-12-2";

        LocalDate actualValue = Task3.parseData(inputString).get();
        LocalDate expectedValue = LocalDate.of(2020, 12, 2);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Backslashes date format, four digits year")
    void backslashesDateFormat_fourDigitsYear() {
        String inputString = "1/3/1976";

        LocalDate actualValue = Task3.parseData(inputString).get();
        LocalDate expectedValue = LocalDate.of(1976, 3, 1);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Backslashes date format, two digits year")
    void backslashesDateFormat_twoDigitsYear() {
        String inputString = "1/3/20";

        LocalDate actualValue = Task3.parseData(inputString).get();
        LocalDate expectedValue = LocalDate.of(2020, 3, 1);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Verbal date provided, tomorrow")
    void verbalDateQuery_tomorrow() {
        String inputString = "tomorrow";

        LocalDate actualValue = Task3.parseData(inputString).get();
        LocalDate expectedValue = LocalDate.now().plusDays(1);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Verbal date provided, today")
    void verbalDateQuery_today() {
        String inputString = "today";

        LocalDate actualValue = Task3.parseData(inputString).get();
        LocalDate expectedValue = LocalDate.now();

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Verbal date provided, yesterday")
    void verbalDateQuery_yesterday() {
        String inputString = "yesterday";

        LocalDate actualValue = Task3.parseData(inputString).get();
        LocalDate expectedValue = LocalDate.now().minusDays(1);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Verbal date provided, incorrect keyword")
    void verbalDateQuery_incorrectKeyword() {
        String inputString = "sudo rm -rf \\";

        boolean actualValue = Task3.parseData(inputString).isPresent();

        assertThat(actualValue).isFalse();
    }

    @Test
    @DisplayName("n days ago, one digit")
    void nDaysAgo_oneDigit() {
        String inputString = "1 day ago";

        LocalDate actualValue = Task3.parseData(inputString).get();
        LocalDate expectedValue = LocalDate.now().minusDays(1);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("n days ago, several digits")
    void nDaysAgo_severalDigits() {
        String inputString = "2234 days ago";

        LocalDate actualValue = Task3.parseData(inputString).get();
        LocalDate expectedValue = LocalDate.now().minusDays(2234);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("n days ago, negative amount of days")
    void nDaysAgo_negativeDaysAmount() {
        String inputString = "-2234 days ago";

        boolean actualValue = Task3.parseData(inputString).isPresent();

        assertThat(actualValue).isFalse();
    }

    @Test
    @DisplayName("Incorrect value provided")
    void incorrectValue() {
        String inputString = "This is exactly not the date format";

        boolean actualValue = Task3.parseData(inputString).isPresent();

        assertThat(actualValue).isFalse();
    }

    @Test
    @DisplayName("Not supported format provided")
    void unsupportedFormat() {
        String inputString = "20111203";

        boolean actualValue = Task3.parseData(inputString).isPresent();

        assertThat(actualValue).isFalse();
    }
}
