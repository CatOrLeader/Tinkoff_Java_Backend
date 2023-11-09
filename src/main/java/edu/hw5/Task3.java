package edu.hw5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public final class Task3 {
    private Task3() {
    }

    public static @NotNull Optional<LocalDate> parseData(@NotNull String string) {
        StringToDateProcessor processor = predefineChainAndGetHead();
        return processor.parseData(string);
    }

    private static StringToDateProcessor predefineChainAndGetHead() {
        DaysAgoFormatToDateProcessor daysAgo =
            new DaysAgoFormatToDateProcessor(null);
        VerbalDateFormatProcessor verbal =
            new VerbalDateFormatProcessor(daysAgo);
        BackslashFourDigitsYearDateFormatProcessor backslashFourDigitYear =
            new BackslashFourDigitsYearDateFormatProcessor(verbal);
        BackslashTwoDigitsYearDateFormatProcessor backslashTwoDigitYear =
            new BackslashTwoDigitsYearDateFormatProcessor(backslashFourDigitYear);
        ISOLocalDateOneDigitFormatProcessor isoLocalDateOneDigit =
            new ISOLocalDateOneDigitFormatProcessor(backslashTwoDigitYear);

        return new ISOLocalDateFormatProcessor(isoLocalDateOneDigit);
    }

    static abstract class StringToDateProcessor {
        public StringToDateProcessor nextProcessor;

        StringToDateProcessor(
            StringToDateProcessor nextProcessor
        ) {
            this.nextProcessor = nextProcessor;
        }

        abstract Optional<LocalDate> parseData(String string);
    }

    static class ISOLocalDateFormatProcessor extends StringToDateProcessor {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

        ISOLocalDateFormatProcessor(
            StringToDateProcessor nextProcessor
        ) {
            super(nextProcessor);
        }

        @Override
        Optional<LocalDate> parseData(String string) {
            try {
                return Optional.of(LocalDate.parse(string, FORMATTER));
            } catch (DateTimeParseException exception) {
                if (nextProcessor != null) {
                    return nextProcessor.parseData(string);
                }
            }

            return Optional.empty();
        }
    }

    static class ISOLocalDateOneDigitFormatProcessor extends StringToDateProcessor {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-d");

        ISOLocalDateOneDigitFormatProcessor(
            StringToDateProcessor nextProcessor
        ) {
            super(nextProcessor);
        }

        @Override
        Optional<LocalDate> parseData(String string) {
            try {
                return Optional.of(LocalDate.parse(string, FORMATTER));
            } catch (DateTimeParseException exception) {
                if (nextProcessor != null) {
                    return nextProcessor.parseData(string);
                }
            }

            return Optional.empty();
        }
    }

    static class BackslashFourDigitsYearDateFormatProcessor extends StringToDateProcessor {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/uuuu");

        BackslashFourDigitsYearDateFormatProcessor(
            StringToDateProcessor nextProcessor
        ) {
            super(nextProcessor);
        }

        @Override
        Optional<LocalDate> parseData(String string) {
            try {
                return Optional.of(LocalDate.parse(string, FORMATTER));
            } catch (DateTimeParseException exception) {
                if (nextProcessor != null) {
                    return nextProcessor.parseData(string);
                }
            }

            return Optional.empty();
        }
    }

    static class BackslashTwoDigitsYearDateFormatProcessor extends StringToDateProcessor {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/uu");

        BackslashTwoDigitsYearDateFormatProcessor(
            StringToDateProcessor nextProcessor
        ) {
            super(nextProcessor);
        }

        @Override
        Optional<LocalDate> parseData(String string) {
            try {
                return Optional.of(LocalDate.parse(string, FORMATTER));
            } catch (DateTimeParseException exception) {
                if (nextProcessor != null) {
                    return nextProcessor.parseData(string);
                }
            }

            return Optional.empty();
        }
    }

    static class VerbalDateFormatProcessor extends StringToDateProcessor {
        VerbalDateFormatProcessor(
            StringToDateProcessor nextProcessor
        ) {
            super(nextProcessor);
        }

        @Override
        Optional<LocalDate> parseData(String string) {
            try {
                KeyWord keyWord = KeyWord.parseValue(string);
                return Optional.of(processQuery(keyWord));
            } catch (Exception exception) {
                if (nextProcessor != null) {
                    return nextProcessor.parseData(string);
                }
            }

            return Optional.empty();
        }

        private LocalDate processQuery(KeyWord keyWord) {
            LocalDate current = LocalDate.now();
            switch (keyWord) {
                case YESTERDAY -> {
                    return current.minusDays(1);
                }
                case TODAY -> {
                    return current;
                }
                case TOMORROW -> {
                    return current.plusDays(1);
                }
                default ->
                    throw new RuntimeException("Exception occurs in the query processing <" + this.getClass() + ">");
            }
        }

        enum KeyWord {
            YESTERDAY("yesterday"), TODAY("today"), TOMORROW("tomorrow");

            final String value;

            KeyWord(String value) {
                this.value = value;
            }

            public static @NotNull KeyWord parseValue(@NotNull String string) {
                if (string.equalsIgnoreCase(YESTERDAY.value)) {
                    return YESTERDAY;
                }

                if (string.equalsIgnoreCase(TODAY.value)) {
                    return TODAY;
                }

                if (string.equalsIgnoreCase(TOMORROW.value)) {
                    return TOMORROW;
                }

                throw new IllegalArgumentException("Incorrect string provided as a keyword");
            }
        }
    }

    static class DaysAgoFormatToDateProcessor extends StringToDateProcessor {
        private static final String STRING_FORMAT = "[0-9]+ days? ago";

        DaysAgoFormatToDateProcessor(
            StringToDateProcessor nextProcessor
        ) {
            super(nextProcessor);
        }

        @Override
        Optional<LocalDate> parseData(String string) {
            try {
                if (!string.matches(STRING_FORMAT)) {
                    throw new IllegalArgumentException("Incorrect string provided");
                }

                int countOfDays = Integer.parseInt(string.split(" ")[0]);

                if (countOfDays < 0) {
                    throw new IllegalArgumentException("Incorrect amount of days ago provided");
                }

                return Optional.of(LocalDate.now().minusDays(countOfDays));
            } catch (Exception exception) {
                if (nextProcessor != null) {
                    return nextProcessor.parseData(string);
                }
            }

            return Optional.empty();
        }
    }
}
