package edu.project3.parsers;

import edu.project3.Configuration;
import edu.project3.LogRecord;
import edu.project3.Metrics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public final class FileLogParser implements LogParser {
    @Override
    public Stream<LogRecord> parse(@NotNull Configuration configuration) {
        List<LogRecord> records = new ArrayList<>();
        List<Path> files = getMatchingFiles(configuration.path());
        for (Path file : files) {
            records.addAll(parseFromFile(configuration, file));
        }

        return records.stream();
    }

    private static List<Path> getMatchingFiles(String pattern) {
        List<Path> files = new ArrayList<>();
        Path initialDir = Path.of(System.getProperty("user.dir"));
        Path pathPattern = Path.of(pattern);

        try {
            Files.walkFileTree(initialDir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (isMatch(file, pathPattern)) {
                        files.add(file);
                    }

                    return FileVisitResult.CONTINUE;
                }

                private static boolean isMatch(Path file, Path pattern) {
                    int size = pattern.getNameCount();
                    for (int i = 0; i < size; i++) {
                        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(
                            "glob:" + pattern.getName(i));

                        if (!matcher.matches(file.getName(i))) {
                            return false;
                        }
                    }

                    return true;
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Incorrect path provided");
        }

        return files;
    }

    private static List<LogRecord> parseFromFile(Configuration configuration, Path path) {
        List<LogRecord> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            reader.lines().forEach(str -> {
                LogRecord log = LogParser.parseLog(str);

                if (!LogParser.isValid(configuration, log)) {
                    return;
                }

                records.add(log);
                Metrics.collect(configuration, log);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }
}
