package edu.hw6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    private static final String filename = "file.txt";
    private static final String resourceFolder = Objects.requireNonNull(Task4Test.class.getClassLoader()
        .getResource("edu/hw6/task4/")).getPath().substring(1);
    private static final Path fileAbsPath = Paths.get(resourceFolder + filename);

    @BeforeEach
    void createInitFile() throws IOException {
        Files.deleteIfExists(fileAbsPath);
        Files.createFile(fileAbsPath);
    }

    static String getContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileAbsPath.toFile()));
        StringBuilder content = new StringBuilder();
        reader.lines().forEach(str -> content.append(str).append("\n"));
        reader.close();
        return content.toString().trim();
    }

    @Test
    @DisplayName("Put the string into file")
    void putString() throws IOException {
        String inputString = "Programming is learned by writing programs. ― Brian Kernighan";

        Task4.writeToFile(inputString, fileAbsPath);
        String actualContent = getContent();

        assertThat(actualContent).isEqualTo(inputString);
    }

    @Test
    @DisplayName("Put empty string into file")
    void putEmptyString() throws IOException {
        String inputString = "";

        Task4.writeToFile(inputString, fileAbsPath);
        String actualContent = getContent();

        assertThat(actualContent).isEqualTo(inputString);
    }

    @Test
    @DisplayName("Put strings into file")
    void putStrings() throws IOException {
        String inputString = """
            Programming is learned by writing programs. ― Brian Kernighan
            Programming is learned by writing programs. ― Brian Kernighan
            Programming is learned by writing programs. ― Brian Kernighan
            """;

        Task4.writeToFile(inputString, fileAbsPath);
        String actualContent = getContent();

        assertThat(actualContent).containsIgnoringNewLines(inputString);
    }
}
