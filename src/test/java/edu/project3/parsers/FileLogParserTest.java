package edu.project3.parsers;

import edu.project3.Configuration;
import edu.project3.LogRecord;
import edu.project3.types.OutputFormat;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;

public class FileLogParserTest {
    private static final String FILE_1_CONTENT = """
        93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        93.180.71.3 - - [17/May/2015:08:05:23 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        80.91.33.133 - - [17/May/2015:08:05:24 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
        217.168.17.5 - - [17/May/2015:08:05:34 +0000] "GET /downloads/product_1 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [17/May/2015:08:05:09 +0000] "GET /downloads/product_2 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)
        """;

    private static final Path ROOT = Paths.get("src", "test", "resources", "edu", "project3",
        "parsers"
    ).toAbsolutePath();
    private static Path dir;
    private static final ArrayList<Path> files = new ArrayList<>();

    @BeforeAll
    static void initialize() throws IOException {
        clear();

        dir = Paths.get(ROOT.toString(), "logs");
        Files.createDirectory(dir);

        files.add(Paths.get(dir.toString(), "single.txt"));
        Files.createFile(files.getLast());
        Files.writeString(
            files.getLast(),
            FILE_1_CONTENT,
            StandardOpenOption.WRITE
        );
    }

    @AfterAll
    static void clear() throws IOException {
        Files.walkFileTree(ROOT, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (dir.equals(ROOT)) {
                    return FileVisitResult.CONTINUE;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    @DisplayName("File does not exists")
    void fileDoesNotExist() {
        String path = "awdsa.dwas";
        Configuration configuration = new Configuration(path, null, null, OutputFormat.MARKDOWN);

        assertThat(new FileLogParser().parse(configuration)).isEmpty();
    }

    @Test
    @DisplayName("Empty folder")
    void emptyFolder() {
        String path = "src/test/resources/edu/project3/parsers/logs/*.txt";
        Configuration configuration = new Configuration(path, null, null, OutputFormat.MARKDOWN);

        assertThat(new FileLogParser().parse(configuration)).isEmpty();
    }

    @Test
    @DisplayName("Single file")
    void singleFile() {
        String path = "logs/single.txt";
        Configuration configuration = new Configuration(path, null, null, OutputFormat.MARKDOWN);

        List<LogRecord> actualValues = new FileLogParser().parse(configuration).toList();
        List<LogRecord> expectedValues = new ArrayList<>(Arrays.stream(FILE_1_CONTENT.split("\n")).map(
            LogParser::parseLog
        ).toList());

        assertThat(actualValues).containsExactlyElementsOf(expectedValues);
    }
}
