package edu.hw6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task1Test {
    private static File TO_READ;
    private static File TO_WRITE;
    private static File EMPTY;

    @BeforeEach void predefineFiles() throws IOException {
        TO_READ = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
            .getResource("edu/hw6/task1/fileToRead.txt")).getPath());
        TO_WRITE = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
            .getResource("edu/hw6/task1/fileToWrite.txt")).getPath());
        EMPTY = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
            .getResource("edu/hw6/task1/empty.txt")).getPath());

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
    @DisplayName("Get current mapped file")
    void getCurrentMapped() {
        assertThat(new DiskMap(TO_READ).getCurrentMappedFile()).isEqualTo(TO_READ);
    }

    @Test
    @DisplayName("Switch to another file which does not exists")
    void switchToAnotherFileNotExist() {
        File nonExistingFile = new File("hidden.txt");
        DiskMap diskMap = new DiskMap(TO_READ);

        assertThatExceptionOfType(IOException.class)
            .isThrownBy(() ->
                diskMap.switchCurrentMappedFile(nonExistingFile));
    }

    @Test
    @DisplayName("Test map file reading possibility")
    void readFromFile() {
        DiskMap diskMap = new DiskMap(TO_READ);

        Set<Map.Entry<String, String>> actualSet = diskMap.entrySet();
        Set<Map.Entry<String, String>> expectedSet = Set.of(
            Map.entry("key", "value"),
            Map.entry("this_pair_key", "this_pair_value"),
            Map.entry("lol", "lol")
        );

        assertThat(actualSet).containsExactlyInAnyOrderElementsOf(expectedSet);
    }

    @Test
    @DisplayName("Test map file reading possibility, put something and read again")
    void readFromFile_PutSomething_ReadAgain() {
        DiskMap diskMap = new DiskMap(TO_READ);
        diskMap.put("new", "message");

        Set<Map.Entry<String, String>> actualSet = diskMap.entrySet();
        Set<Map.Entry<String, String>> expectedSet = Set.of(
            Map.entry("key", "value"),
            Map.entry("this_pair_key", "this_pair_value"),
            Map.entry("lol", "lol"),
            Map.entry("new", "message")
        );

        assertThat(actualSet).containsExactlyInAnyOrderElementsOf(expectedSet);
    }

    @Test
    @DisplayName("Put all collection to map")
    void putAllToMap() throws IOException {
        DiskMap diskMap = new DiskMap(TO_READ);
        diskMap.putAll(Map.of(
            "new", "value",
            "gojo", "geto"
        ));

        try (BufferedReader reader = new BufferedReader(new FileReader(TO_READ))) {
            List<String> actualValues = new ArrayList<>();
            reader.lines().forEach(actualValues::add);
            List<String> expectedValues = List.of(
                "key:value",
                "this_pair_key:this_pair_value",
                "lol:lol",
                "new:value",
                "gojo:geto"
            );

            assertThat(actualValues).containsExactlyInAnyOrderElementsOf(expectedValues);
        }
    }

    @Test
    @DisplayName("Remove from map")
    void removeFromMap() throws IOException {
        DiskMap diskMap = new DiskMap(TO_READ);
        diskMap.remove("key");

        try (BufferedReader reader = new BufferedReader(new FileReader(TO_READ))) {
            List<String> actualValues = new ArrayList<>();
            reader.lines().forEach(actualValues::add);
            List<String> expectedValues = List.of(
                "this_pair_key:this_pair_value",
                "lol:lol"
            );

            assertThat(actualValues).containsExactlyInAnyOrderElementsOf(expectedValues);
        }
    }

    @Test
    @DisplayName("Test map writing possibility")
    void writeToFile() throws IOException {
        DiskMap diskMap = new DiskMap(TO_READ);
        diskMap.switchCurrentMappedFile(TO_WRITE);
        diskMap.writeToFile(TO_WRITE);

        try (BufferedReader reader = new BufferedReader(new FileReader(TO_WRITE))) {
            List<String> actualValues = new ArrayList<>();
            reader.lines().forEach(actualValues::add);
            List<String> expectedValues = List.of(
                "key:value",
                "this_pair_key:this_pair_value",
                "lol:lol"
            );

            assertThat(actualValues).containsExactlyInAnyOrderElementsOf(expectedValues);
        }
    }

    @Test
    @DisplayName("Test map writing possibility, put something and read file to check")
    void writeToFile_PutSomethingInMap_ReadFile() throws IOException {
        DiskMap diskMap = new DiskMap(TO_READ);
        diskMap.switchCurrentMappedFile(TO_WRITE);
        diskMap.put("test", "added");

        try (BufferedReader reader = new BufferedReader(new FileReader(TO_WRITE))) {
            List<String> actualValues = new ArrayList<>();
            reader.lines().forEach(actualValues::add);
            List<String> expectedValues = List.of(
                "key:value",
                "this_pair_key:this_pair_value",
                "lol:lol",
                "test:added"
            );

            assertThat(actualValues).containsExactlyInAnyOrderElementsOf(expectedValues);
        }
    }

    @Test
    @DisplayName("Synchronize file, add something in file and check the default map methods and fields to be updated")
    void synchronizeWithFile_AddToFile_CheckMethodsToBeUpdated() throws IOException {
        DiskMap diskMap = new DiskMap(TO_READ);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TO_READ))) {
            writer.write("new:value\nits:interesting");
            writer.newLine();
            writer.flush();
        }

        assertThat(diskMap.size()).isEqualTo(5);
        assertThat(diskMap.containsKey("new")).isTrue();
        assertThat(diskMap.containsValue("interesting")).isTrue();
    }

    @Test
    @DisplayName("Synchronize file, add something in file and get from map")
    void synchronizeWithFile_AddToFile_GetFromMap() throws IOException {
        DiskMap diskMap = new DiskMap(TO_READ);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TO_READ))) {
            writer.write("new:value\nits:interesting");
            writer.newLine();
            writer.flush();
        }

        assertThat(diskMap.get("new")).isEqualTo("value");
    }

    @Test
    @DisplayName("Synchronize file, add something in file and check the default map sets to be updated")
    void synchronizeWithFile_AddToFile_CheckSetsToBeUpdated() throws IOException {
        DiskMap diskMap = new DiskMap(TO_READ);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TO_READ))) {
            writer.write("new:value\nits:interesting");
            writer.newLine();
            writer.flush();
        }

        List<String> expectedKeysStr = List.of("new", "its", "lol", "key", "this_pair_key");
        List<String> expectedValuesStr = List.of("value", "interesting", "lol", "value", "this_pair_value");

        Set<String> actualKeys = diskMap.keySet();
        Set<String> expectedKeys = Set.copyOf(expectedKeysStr);
        assertThat(actualKeys).containsExactlyInAnyOrderElementsOf(expectedKeys);

        Collection<String> actualValues = diskMap.values();
        assertThat(actualValues).containsExactlyInAnyOrderElementsOf(expectedValuesStr);

        Set<Map.Entry<String, String>> actualEntries = diskMap.entrySet();
        Set<Map.Entry<String, String>> expectedEntries = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            expectedEntries.add(new AbstractMap.SimpleEntry<>(expectedKeysStr.get(i), expectedValuesStr.get(i)));
        }
        assertThat(actualEntries).containsExactlyInAnyOrderElementsOf(expectedEntries);
    }

    @Test
    @DisplayName("Synchronize file and clear")
    void synchronizeWithFile_ClearMap() throws IOException {
        DiskMap diskMap = new DiskMap(TO_READ);
        diskMap.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(TO_READ))) {
            assertThat(reader.readLine()).isNull();
        }
        assertThat(diskMap.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Empty file reading")
    void emptyFileReading() {
        DiskMap diskMap = new DiskMap(EMPTY);

        assertThat(diskMap.entrySet()).isEmpty();
    }

    @Test
    @DisplayName("Incorrect file provided, initializing")
    void incorrectFileProvided_read() {
        File nonExistingFile = new File("hidden.txt");

        Assertions.assertThrows(RuntimeException.class, () ->
            new DiskMap(nonExistingFile));
    }
}
