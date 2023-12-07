package edu.hw6;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.Task3.AbstractFilter;
import static edu.hw6.Task3.AbstractFilter.globMatches;
import static edu.hw6.Task3.AbstractFilter.largerThan;
import static edu.hw6.Task3.AbstractFilter.magicNumber;
import static edu.hw6.Task3.AbstractFilter.regexContains;
import static org.assertj.core.api.Assertions.assertThat;

public final class Task3Test {
    private static final File file = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
        .getResource("edu/hw6/task3/file.txt")).getPath());
    private static final File svg = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
        .getResource("edu/hw6/task3/icon.svg")).getPath());
    private static final File png = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
        .getResource("edu/hw6/task3/champloo.png")).getPath());
    private static final Path fileAbsPath = file.toPath();
    private static final Path svgAbsPath = svg.toPath();
    private static final Path pngAbsPath = png.toPath();
    private static final File folder = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
        .getResource("edu/hw6/task3")).getPath());

    public static final AbstractFilter regularFile = Files::isRegularFile;
    public static final AbstractFilter readable = Files::isReadable;

    @BeforeAll
    static void createInitFile() throws IOException {
        Files.deleteIfExists(fileAbsPath);
        Files.createFile(fileAbsPath);
    }

    @Test
    @DisplayName("Is regular file")
    void isRegularFileFound() {
        List<Path> expectedFiles = List.of(fileAbsPath, svgAbsPath, pngAbsPath);
        List<Path> actualFiles = new ArrayList<>();
        try (
            DirectoryStream<Path> actualEntries = Files.newDirectoryStream(folder.toPath(), regularFile)
        ) {
            for (Path actualEntry : actualEntries) {
                actualFiles.add(actualEntry);
            }
        } catch (Exception ignored) {
            Assertions.fail();
        }

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }

    @Test
    @DisplayName("Is readable file")
    void isReadableFileFound() {
        List<Path> expectedFiles = List.of(fileAbsPath, svgAbsPath, pngAbsPath);
        List<Path> actualFiles = new ArrayList<>();
        try (
            DirectoryStream<Path> actualEntries = Files.newDirectoryStream(folder.toPath(), readable)
        ) {
            for (Path actualEntry : actualEntries) {
                actualFiles.add(actualEntry);
            }
        } catch (Exception ignored) {
            Assertions.fail();
        }

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }

    @Test
    @DisplayName("Is larger than 20 kb found")
    void isLargerThan20KBFound() {
        List<Path> expectedFiles = List.of(svgAbsPath, pngAbsPath);
        List<Path> actualFiles = new ArrayList<>();
        try (
            DirectoryStream<Path> actualEntries = Files.newDirectoryStream(folder.toPath(), largerThan(20480))
        ) {
            for (Path actualEntry : actualEntries) {
                actualFiles.add(actualEntry);
            }
        } catch (Exception ignored) {
            Assertions.fail();
        }

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }

    @Test
    @DisplayName("Is SVG format")
    void isSVGFormat() {
        List<Path> expectedFiles = List.of(svgAbsPath);
        List<Path> actualFiles = new ArrayList<>();
        try (
            DirectoryStream<Path> actualEntries = Files.newDirectoryStream(folder.toPath(), globMatches("*.svg"))
        ) {
            for (Path actualEntry : actualEntries) {
                actualFiles.add(actualEntry);
            }
        } catch (Exception ignored) {
            Assertions.fail();
        }

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }

    @Test
    @DisplayName("Regexp test")
    void regexpInFilename() {
        List<Path> expectedFiles = List.of(svgAbsPath, fileAbsPath);
        List<Path> actualFiles = new ArrayList<>();
        try (DirectoryStream<Path> actualEntries = Files.newDirectoryStream(
            folder.toPath(),
            regexContains(".*i.*")
        )) {
            for (Path actualEntry : actualEntries) {
                actualFiles.add(actualEntry);
            }
        } catch (Exception ignored) {
            Assertions.fail();
        }

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }

    @Test
    @DisplayName("Magic initial identifiers")
    void magicInitIdentifiers() {
        List<Path> expectedFiles = List.of(pngAbsPath);
        List<Path> actualFiles = new ArrayList<>();
        try (
            DirectoryStream<Path> actualEntries = Files.newDirectoryStream(
                folder.toPath(),
                magicNumber(-0x77, 'P', 'N', 'G')
            )
        ) {
            for (Path actualEntry : actualEntries) {
                actualFiles.add(actualEntry);
            }
        } catch (Exception ignored) {
            Assertions.fail();
        }

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }

    @Test
    @DisplayName("Chaining filters, only one correct")
    void chainingFilters_oneCorrect() {
        List<Path> expectedFiles = List.of(svgAbsPath);
        try (
            DirectoryStream<Path> actualEntries = Files.newDirectoryStream(
                folder.toPath(),
                regexContains("i").and(largerThan(20480))
            )
        ) {
            for (Path actualEntry : actualEntries) {
                assertThat(actualEntry).isIn(expectedFiles);
            }
        } catch (Exception ignored) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Chaining filters, all correct")
    void chainingFilters_allCorrect() {
        List<Path> expectedFiles = List.of(svgAbsPath, fileAbsPath, pngAbsPath);
        try (
            DirectoryStream<Path> actualEntries = Files.newDirectoryStream(
                folder.toPath(),
                globMatches("*.txt").or(largerThan(20480))
            )
        ) {
            for (Path actualEntry : actualEntries) {
                assertThat(actualEntry).isIn(expectedFiles);
            }
        } catch (Exception ignored) {
            Assertions.fail();
        }
    }
}
