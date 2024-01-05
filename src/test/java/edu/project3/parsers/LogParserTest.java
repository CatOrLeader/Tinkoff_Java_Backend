package edu.project3.parsers;

import edu.project3.Configuration;
import edu.project3.LogRecord;
import edu.project3.types.HttpRequestType;
import edu.project3.types.OutputFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LogParserTest {
    @Test
    @DisplayName("Correct log provided")
    void correctLog() {
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET "
                     + "/downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 "
                     + "(0.8.16~exp12ubuntu10.21)\"\n";

        LogRecord actualValue = LogParser.parseLog(log);
        LogRecord expectedValue = LogRecord.Builder.newInstance()
            .setAddress("93.180.71.3")
            .setUser("-")
            .setTime(OffsetDateTime.of(2015, 5, 17, 8, 5, 32, 0, ZoneOffset.UTC))
            .setRequest(HttpRequestType.GET)
            .setResource("/downloads/product_1")
            .setProtocol("HTTP/1.1")
            .setStatus(304)
            .setBodyBytesSent(0)
            .setHttpReferer("-")
            .setHttpUserAgent("Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)")
            .build();

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect time")
    void incorrectTime() {
        String log = "93.180.71.3 - - [17/May/205:08:05:32 +0000] \"GET "
                     + "/downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 "
                     + "(0.8.16~exp12ubuntu10.21)\"\n";

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> LogParser.parseLog(log))
            .withMessage("Incorrect time provided");
    }

    @Test
    @DisplayName("Incorrect request type")
    void incorrectRequestType() {
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GOT "
                     + "/downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 "
                     + "(0.8.16~exp12ubuntu10.21)\"\n";

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> LogParser.parseLog(log))
            .withMessage("Incorrect request type provided");
    }

    @Test
    @DisplayName("Incorrect status")
    void incorrectStatus() {
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET "
                     + "/downloads/product_1 HTTP/1.1\" 30a4 0 \"-\" \"Debian APT-HTTP/1.3 "
                     + "(0.8.16~exp12ubuntu10.21)\"\n";

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> LogParser.parseLog(log))
            .withMessage("Incorrect status provided");
    }

    @Test
    @DisplayName("Incorrect body bytes sent")
    void incorrectBodyBytesSent() {
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET "
                     + "/downloads/product_1 HTTP/1.1\" 304 3211s1 \"-\" \"Debian APT-HTTP/1.3 "
                     + "(0.8.16~exp12ubuntu10.21)\"\n";

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> LogParser.parseLog(log))
            .withMessage("Incorrect number of bytes sent provided");
    }

    @Test
    @DisplayName("Log is before the from option")
    void logIsBeforeFrom() {
        Configuration configuration = new Configuration("path",
            LocalDate.of(2015, 5, 18),
            null, OutputFormat.MARKDOWN
        );
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET "
                     + "/downloads/product_1 HTTP/1.1\" 304 321 \"-\" \"Debian APT-HTTP/1.3 "
                     + "(0.8.16~exp12ubuntu10.21)\"\n";
        LogRecord logRecord = LogParser.parseLog(log);

        assertThat(LogParser.isInvalid(configuration, logRecord)).isTrue();
    }

    @Test
    @DisplayName("Log is after the to option")
    void logIsAfterTo() {
        Configuration configuration = new Configuration("path", null,
            LocalDate.of(2015, 5, 16), OutputFormat.MARKDOWN
        );
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET "
                     + "/downloads/product_1 HTTP/1.1\" 304 321 \"-\" \"Debian APT-HTTP/1.3 "
                     + "(0.8.16~exp12ubuntu10.21)\"\n";
        LogRecord logRecord = LogParser.parseLog(log);

        assertThat(LogParser.isInvalid(configuration, logRecord)).isTrue();
    }

    @Test
    @DisplayName("Log is correct according to from and to requests")
    void logIsCorrectAccordingToFromAndTo() {
        Configuration configuration = new Configuration(
            "path",
            LocalDate.of(2015, 5, 16),
            LocalDate.of(2015, 5, 18),
            OutputFormat.MARKDOWN
        );
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET "
                     + "/downloads/product_1 HTTP/1.1\" 304 321 \"-\" \"Debian APT-HTTP/1.3 "
                     + "(0.8.16~exp12ubuntu10.21)\"\n";
        LogRecord logRecord = LogParser.parseLog(log);

        assertThat(LogParser.isInvalid(configuration, logRecord)).isFalse();
    }
}
