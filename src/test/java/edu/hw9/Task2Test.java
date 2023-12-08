package edu.hw9;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    private static final Path ROOT = Paths.get("src", "test", "resources", "edu", "hw9");
    private static final File FILE1 = new File(Paths.get(ROOT.toString(), "file1.txt").toString());
    private static final File FILE2 = new File(Paths.get(ROOT.toString(), "file2.txt").toString());

    @BeforeAll
    static void startUp() throws IOException {
        cleanUp();
        try {
            Files.createDirectory(ROOT);
        } catch (FileAlreadyExistsException ignored) {
        }
        for (int i = 0; i < 1001; i++) {
            Files.createFile(Paths.get(ROOT.toString(), String.valueOf(i)));
        }

        try (RandomAccessFile raf1 = new RandomAccessFile(FILE1, "rw");
             RandomAccessFile raf2 = new RandomAccessFile(FILE2, "rw")) {
            raf1.setLength(1024);
            raf2.setLength(512);
        }
    }

    @AfterAll
    static void cleanUp() throws IOException {
        if (!ROOT.toFile().exists()) {
            return;
        }
        for (File file : Objects.requireNonNull(ROOT.toFile().listFiles())) {
            Files.delete(file.toPath());
        }
        Files.deleteIfExists(ROOT);
    }

    @Test
    @DisplayName("Find directories with more than 1000 files")
    void findDirWithMoreThan1000Files() {
        assertThat(Task2.findDirectoriesWithMoreThan1000Files().stream()
            .map(File::getAbsolutePath)).contains(ROOT.toFile().getAbsolutePath());
    }

    @Test
    @DisplayName("Find file with certain extension and size, only one found")
    void findFileWithCertainExtensionAndSize_OnlyOneFound() {
        assertThat(Task2.findFilesWithExtensionAndLargerThan("txt", 800).stream()
            .map(File::getAbsolutePath)).contains(FILE1.getAbsolutePath())
            .doesNotContain(FILE2.getAbsolutePath());
    }

    @Test
    @DisplayName("Find file with certain extension and size, both found")
    void findFileWithCertainExtensionAndSize_BothFound() {
        assertThat(Task2.findFilesWithExtensionAndLargerThan("txt", 511).stream()
            .map(File::getAbsolutePath)).contains(FILE1.getAbsolutePath()).contains(FILE2.getAbsolutePath());
    }
}
