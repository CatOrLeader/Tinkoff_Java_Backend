package edu.project3.parsers;

import edu.project3.Configuration;
import edu.project3.LogRecord;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public final class FileLogParser implements LogParser {
    private static final String THROWABLE_MSG = "Incorrect path provided";

    @Override
    public Stream<LogRecord> parse(@NotNull Configuration configuration) {
        List<LogRecord> records = new ArrayList<>();

        List<Path> paths;
        try {
            paths = getMatchingFiles(configuration.path());
        } catch (IOException e) {
            throw new IllegalArgumentException(THROWABLE_MSG);
        }

        for (Path path : paths) {
            if (!path.toFile().isFile() || !Files.isReadable(path)) {
                continue;
            }
            records.addAll(parseFromFile(configuration, path));
        }

        return records.stream();
    }

    private static List<Path> getMatchingFiles(String pattern) throws IOException {
        try {
            Path path = Path.of(pattern);
            return getFromAbsPath(path);
        } catch (InvalidPathException ignored) {
        }

        return getFromWildcard(pattern);
    }

    private static List<Path> getFromAbsPath(Path path) {
        List<Path> paths = new ArrayList<>();

        if (path.isAbsolute()) {
            File file = path.toFile();

            if (!file.exists()) {
                throw new IllegalArgumentException(THROWABLE_MSG);
            }

            if (file.isFile()) {
                paths.add(file.toPath());
            } else if (file.isDirectory()) {
                for (File iFile : Objects.requireNonNull(file.listFiles())) {
                    paths.add(iFile.toPath());
                }
            }

        }

        return paths;
    }

    private static List<Path> getFromWildcard(String pattern) throws IOException {
        List<Path> paths = new ArrayList<>();

        Path root = Path.of(System.getProperty("user.dir"));
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(
            "glob:" + pattern);

        Files.walkFileTree(root, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (!file.toFile().isFile()) {
                        return FileVisitResult.CONTINUE;
                    }

                    if (matcher.matches(root.relativize(file))) {
                        paths.add(file);
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            }
        );

        return paths;
    }

    private static List<LogRecord> parseFromFile(Configuration configuration, Path path) {
        List<LogRecord> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            reader.lines().forEach(str -> {
                LogRecord log = LogParser.parseLog(str);

                if (LogParser.isInvalid(configuration, log)) {
                    return;
                }

                records.add(log);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return records;
    }
}
