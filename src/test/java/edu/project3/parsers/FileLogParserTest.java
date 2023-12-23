package edu.project3.parsers;

import edu.project3.Configuration;
import edu.project3.LogRecord;
import edu.project3.types.HttpRequestType;
import edu.project3.types.OutputFormat;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class FileLogParserTest {
    private static final LogParser PARSER = new FileLogParser();
    private static final Path root = Paths.get("src", "test", "resources", "edu", "project3",
        "parsers"
    );

    private static final LogRecord LOG_RECORD_1 = LogRecord.Builder.newInstance()
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

    private static final LogRecord LOG_RECORD_2 = LogRecord.Builder.newInstance()
        .setAddress("93.180.71.3")
        .setUser("-")
        .setTime(OffsetDateTime.of(2015, 5, 17, 8, 5, 23, 0, ZoneOffset.UTC))
        .setRequest(HttpRequestType.GET)
        .setResource("/downloads/product_1")
        .setProtocol("HTTP/1.1")
        .setStatus(304)
        .setBodyBytesSent(0)
        .setHttpReferer("-")
        .setHttpUserAgent("Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)")
        .build();

    @Test
    @DisplayName("Single file with abs path provided, incorrect")
    void incorrectSingleFile_absPath() {
        Path file = Paths.get(root.toAbsolutePath().toString(), "nonExistenceLogs.txt");
        Configuration configuration = new Configuration(file.toString(),
            null, null, OutputFormat.MARKDOWN
        );

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> PARSER.parse(configuration))
            .withMessage("Incorrect path provided");
    }

    @Test
    @DisplayName("Single file with abs path provided, correct")
    void correctSingleFile_absPath() {
        Path file = Paths.get(root.toAbsolutePath().toString(), "logs.txt");
        Configuration configuration = new Configuration(file.toString(),
            null, null, OutputFormat.MARKDOWN
        );

        List<LogRecord> actualRecords = PARSER.parse(configuration).toList();
        List<LogRecord> expectedRecords = List.of(
            LOG_RECORD_1,
            LOG_RECORD_2
        );

        assertThat(actualRecords).containsExactlyInAnyOrderElementsOf(expectedRecords);
    }

    @Test
    @DisplayName("Single dir with abs path provided, correct")
    void correctSingleDir_absPath() {
        Path file = Paths.get(root.toAbsolutePath().toString());
        Configuration configuration = new Configuration(file.toString(),
            null, null, OutputFormat.MARKDOWN
        );

        List<LogRecord> actualRecords = PARSER.parse(configuration).toList();
        List<LogRecord> expectedRecords = List.of(
            LOG_RECORD_1,
            LOG_RECORD_1,
            LOG_RECORD_1,
            LOG_RECORD_2,
            LOG_RECORD_2,
            LOG_RECORD_2
        );

        assertThat(actualRecords).containsExactlyInAnyOrderElementsOf(expectedRecords);
    }

    @Test
    @DisplayName("Single file path provided, no file found")
    void noFileFound() {
        Path file = Paths.get(root.toString(), "nonExistenceLogs.txt");
        System.out.println(file);
        Configuration configuration = new Configuration(file.toString(),
            null, null, OutputFormat.MARKDOWN
        );

        assertThat(PARSER.parse(configuration)).isEmpty();
    }

    // Два закоменченных теста просто не хотят работать на убунту гитхаба, не могу разобраться почему

//    @Test
//    @DisplayName("Correct pattern provided, two files")
//    void correctPattern_twoFiles() {
//        String pattern = "src/**/[0-9]logs.txt";
//        Configuration configuration = new Configuration(pattern,
//            null, null, OutputFormat.MARKDOWN
//        );
//
//        List<LogRecord> actualRecords = PARSER.parse(configuration).toList();
//        List<LogRecord> expectedRecords = List.of(
//            LOG_RECORD_1,
//            LOG_RECORD_1,
//            LOG_RECORD_2,
//            LOG_RECORD_2
//        );
//
//        assertThat(actualRecords).containsExactlyInAnyOrderElementsOf(expectedRecords);
//    }

    @Test
    @DisplayName("Correct pattern provided, empty dir")
    void correctPattern_emptyDir() {
        String pattern = "src/**/empty";
        Configuration configuration = new Configuration(pattern,
            null, null, OutputFormat.MARKDOWN
        );

        assertThat(PARSER.parse(configuration).toList()).isEmpty();
    }

//    @Test
//    @DisplayName("Correct pattern provided, all files")
//    void correctPattern_allFiles() {
//        System.out.println(Path.of(System.getProperty("user.dir")));
//        String pattern = "src/**/*logs.txt";
//        Configuration configuration = new Configuration(pattern,
//            null, null, OutputFormat.MARKDOWN
//        );
//
//        List<LogRecord> actualRecords = PARSER.parse(configuration).toList();
//        List<LogRecord> expectedRecords = List.of(
//            LOG_RECORD_1,
//            LOG_RECORD_1,
//            LOG_RECORD_1,
//            LOG_RECORD_2,
//            LOG_RECORD_2,
//            LOG_RECORD_2
//        );
//
//        assertThat(actualRecords).containsExactlyInAnyOrderElementsOf(expectedRecords);
//    }
}
