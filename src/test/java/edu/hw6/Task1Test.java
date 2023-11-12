package edu.hw6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.Task1.DiskMap;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    private static File TO_READ;
    private static File TO_WRITE;
    private static File EMPTY;

    @BeforeEach void predefineFiles() throws URISyntaxException, IOException {
        TO_READ = Paths.get(
            Objects.requireNonNull(Task1Test.class.getResource("task1/fileToRead.txt")).toURI()).toFile();
        TO_WRITE = Paths.get(
            Objects.requireNonNull(Task1Test.class.getResource("task1/fileToWrite.txt")).toURI()).toFile();
        EMPTY = Paths.get(
            Objects.requireNonNull(Task1Test.class.getResource("task1/empty.txt")).toURI()).toFile();

        BufferedWriter writerToRead = Files.newBufferedWriter(TO_READ.toPath(), StandardOpenOption.TRUNCATE_EXISTING);
        writerToRead.write("""
            key:value
            this_pair_key:this_pair_value
            lol:lol

            """);
        writerToRead.close();

        BufferedWriter writerToWrite = Files.newBufferedWriter(TO_WRITE.toPath(), StandardOpenOption.TRUNCATE_EXISTING);
        writerToWrite.close();

        BufferedWriter writerEmpty = Files.newBufferedWriter(EMPTY.toPath(), StandardOpenOption.TRUNCATE_EXISTING);
        writerEmpty.close();
    }

    @Test
    @DisplayName("Test map file reading possibility")
    void readFromFile() throws IOException {
        DiskMap diskMap = new DiskMap();
        diskMap.readFromFile(TO_READ);

        Set<Map.Entry<String, String>> actualSet = diskMap.entrySet();
        Set<Map.Entry<String, String>> expectedSet = Set.of(
            Map.entry("key", "value"),
            Map.entry("this_pair_key", "this_pair_value"),
            Map.entry("lol", "lol")
        );

        assertThat(actualSet).containsExactlyInAnyOrderElementsOf(expectedSet);
    }

    @Test
    @DisplayName("Test map writing possibility")
    void writeToFile() throws IOException {
        DiskMap diskMap = new DiskMap();
        diskMap.readFromFile(TO_READ);
        diskMap.put("test", "added");
        diskMap.writeToFile(TO_WRITE);

        BufferedReader reader = new BufferedReader(new FileReader(TO_WRITE));
        List<String> actualValues = new ArrayList<>();
        reader.lines().forEach(actualValues::add);
        reader.close();
        List<String> expectedValues = List.of(
            "key:value",
            "this_pair_key:this_pair_value",
            "lol:lol",
            "test:added"
        );

        assertThat(actualValues).containsExactlyInAnyOrderElementsOf(expectedValues);
    }

    @Test
    @DisplayName("Empty file reading")
    void emptyFileReading() throws IOException {
        DiskMap diskMap = new DiskMap();
        diskMap.readFromFile(EMPTY);

        assertThat(diskMap.entrySet()).isEmpty();
    }

    @Test
    @DisplayName("Incorrect file provided")
    void incorrectFileProvided() {
        File nonExistingFile = new File("hidden.txt");
        DiskMap diskMap = new DiskMap();

        Assertions.assertThrows(IOException.class, () -> diskMap.readFromFile(nonExistingFile));
    }
}
