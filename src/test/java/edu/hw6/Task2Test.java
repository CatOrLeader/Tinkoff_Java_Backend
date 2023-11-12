package edu.hw6;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    private static final File folder = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
        .getResource("edu/hw6/task2")).getPath());
    private static final File file = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
        .getResource("edu/hw6/task2/file.txt")).getPath());
    private static final Path fileAbsPath = file.toPath();

    @BeforeAll
    static void createInitFile() throws IOException {
        new Task2Test().clearFiles();
        Files.deleteIfExists(file.toPath());
        Files.createFile(file.toPath());
    }

    @AfterEach
    void clearFiles() throws IOException {
        for (File current : Objects.requireNonNull(folder.listFiles())) {
            if (current.getName().equals(file.getName())) {
                continue;
            }
            Files.delete(current.toPath());
        }
    }

    @Test
    @DisplayName("File does not exists")
    void fileDoesNotExists() {
        File file = new File("unlockPentagon.sh");

        Assertions.assertThrows(IOException.class, () -> Task2.cloneFile(file.toPath()));
    }

    @Test
    @DisplayName("Create sequentially fifteen files")
    void fifteenFilesCreationSequential() throws IOException {
        File file = fileAbsPath.toFile();
        for (int i = 0; i < 15; i++) {
            Task2.cloneFile(fileAbsPath);
        }

        List<File> actualFiles = List.of(Objects.requireNonNull(folder.listFiles()));
        List<File> expectedFiles = new ArrayList<>();
        expectedFiles.add(new File(folder.getPath() + File.separator + file.getName()));
        for (int i = 1; i < 15 + 1; i++) {
            expectedFiles.add(new File(folder.getPath() + File.separator + file.getName().substring(
                0,
                file.getName().lastIndexOf(".")
            )
                                       + " - copy" + (i == 1 ? "" : " (" + i + ")")
                                       + file.getName().substring(
                file.getName().lastIndexOf(".")
            )
            ));
        }

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }

    @Test
    @DisplayName("Create sequentially fifteen files, delete two and create two more")
    void fifteenFilesCreationSequential_deleteTwoCreateTwo() throws IOException {
        File file = fileAbsPath.toFile();
        for (int i = 0; i < 15; i++) {
            Task2.cloneFile(fileAbsPath);
        }

        List<File> actualFiles = List.of(Objects.requireNonNull(folder.listFiles()));
        List<File> expectedFiles = new ArrayList<>();
        expectedFiles.add(new File(folder.getPath() + File.separator + file.getName()));
        for (int i = 1; i < 15 + 1; i++) {
            expectedFiles.add(new File(folder.getPath() + File.separator + file.getName().substring(
                0,
                file.getName().lastIndexOf(".")
            )
                                       + " - copy" + (i == 1 ? "" : " (" + i + ")")
                                       + file.getName().substring(
                file.getName().lastIndexOf(".")
            )
            ));
        }
        Files.deleteIfExists(expectedFiles.get(3).toPath());
        Files.deleteIfExists(expectedFiles.get(6).toPath());
        for (int i = 0; i < 2; i++) {
            Task2.cloneFile(fileAbsPath);
        }

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }
}
