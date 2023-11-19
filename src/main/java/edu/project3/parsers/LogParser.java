package edu.project3.parsers;

import edu.project3.Configuration;
import edu.project3.LogRecord;
import edu.project3.Metrics;
import edu.project3.types.HttpRequestType;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import static edu.project3.LogRecord.Builder;

public sealed interface LogParser permits FileLogParser, RemoteLogParser {
    Stream<LogRecord> parse(@NotNull Configuration configuration) throws IllegalArgumentException;

    static @NotNull LogRecord parseLog(@NotNull String string)
        throws IllegalArgumentException, ExceptionInInitializerError {
        return parseAddress(Builder.newInstance(), string.trim().split(" ")).build();
    }

    static boolean isValid(Configuration configuration, LogRecord log) {
        if (configuration.from() != null) {
            if (log.getTime().isBefore(OffsetDateTime.from(configuration.from()))) {
                return false;
            }
        }

        if (configuration.to() != null) {
            return !log.getTime().isAfter(OffsetDateTime.from(configuration.to()));
        }

        return true;
    }

    private static Builder parseAddress(Builder builder, String[] string) {
        final int index = 0;

        builder.setAddress(string[index]);

        return parseUser(builder, string);
    }

    private static Builder parseUser(Builder builder, String[] string) {
        final int index = 2;

        builder.setUser(string[index]);

        return parseTime(builder, string);
    }

    private static Builder parseTime(Builder builder, String[] string) {
        final int indexTime = 3;
        final int indexOffset = 4;

        String element = string[indexTime] + " " + string[indexOffset];
        int elemSize = element.length();
        String timeStr = element.substring(1, elemSize - 1).trim();

        try {
            OffsetDateTime time = OffsetDateTime.parse(timeStr, DateTimeFormatter.ofPattern(
                "dd/MMM/uuuu:HH:mm:ss Z", Locale.US
            ));

            builder.setTime(time);

            return parseRequest(builder, string);
        } catch (DateTimeParseException e) {
            Metrics.UNPARSED_LOGS.add(String.join(" ", string));
            throw new IllegalArgumentException("Incorrect time provided");
        }
    }

    private static Builder parseRequest(Builder builder, String[] string) {
        final int index = 5;

        String request = string[index].substring(1);
        Optional<HttpRequestType> maybeRequestType = HttpRequestType.parseRequestType(request);

        if (maybeRequestType.isEmpty()) {
            Metrics.UNPARSED_LOGS.add(String.join(" ", string));
            throw new IllegalArgumentException("Incorrect request type provided");
        }

        builder.setRequest(maybeRequestType.get());

        return parseResource(builder, string);
    }

    private static Builder parseResource(Builder builder, String[] string) {
        final int index = 6;

        builder.setResource(string[index]);

        return parseProtocol(builder, string);
    }

    private static Builder parseProtocol(Builder builder, String[] string) {
        final int index = 7;

        String element = string[index];
        int elemSize = element.length();
        String protocol = element.substring(0, elemSize - 1);

        builder.setProtocol(protocol);

        return parseStatus(builder, string);
    }

    private static Builder parseStatus(Builder builder, String[] string) {
        final int index = 8;

        try {
            builder.setStatus(Integer.parseInt(string[index]));

            return parseBodyBytesSent(builder, string);
        } catch (NumberFormatException e) {
            Metrics.UNPARSED_LOGS.add(String.join(" ", string));
            throw new IllegalArgumentException("Incorrect status provided");
        }
    }

    private static Builder parseBodyBytesSent(Builder builder, String[] string) {
        final int index = 9;

        try {
            builder.setBodyBytesSent(Integer.parseInt(string[index]));

            return parseHttpReferer(builder, string);
        } catch (NumberFormatException e) {
            Metrics.UNPARSED_LOGS.add(String.join(" ", string));
            throw new IllegalArgumentException("Incorrect number of bytes sent provided");
        }
    }

    private static Builder parseHttpReferer(Builder builder, String[] string) {
        final int index = 10;

        String element = string[index];
        int elemSize = element.length();

        builder.setHttpReferer(element.substring(1, elemSize - 1));

        return parseHttpUserAgent(builder, string);
    }

    private static Builder parseHttpUserAgent(Builder builder, String[] string) {
        final int index = 11;

        StringBuilder sb = new StringBuilder();
        for (int i = index; i < string.length; i++) {
            sb.append(string[i]).append(" ");
        }
        String element = sb.toString().trim();
        int elemSize = element.length();

        builder.setHttpUserAgent(element.substring(1, elemSize - 1));

        return builder;
    }
}
