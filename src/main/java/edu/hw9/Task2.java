package edu.hw9;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public final class Task2 {
    private Task2() {
    }

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_DIR = "user.dir";

    public static List<File> findDirectoriesWithMoreThan1000Files() {
        final int n = 1000;

        // To make the tests faster, assume that the root is the current working directory
        // In the function below - same
        // File root = FileSystemView.getFileSystemView().getHomeDirectory();
        File root = new File(System.getProperty(USER_DIR));
        Set<File> commonSet = Collections.synchronizedSet(new HashSet<>());

        try (ForkJoinPool pool = ForkJoinPool.commonPool()) {
            RecursiveFinder finder = new RecursiveFinder(root, AbstractFilter.isDirectory()
                .and(AbstractFilter.moreThanNFiles(n)), commonSet);
            pool.invoke(finder);
        }

        return commonSet.stream().toList();
    }

    public static List<File> findFilesWithExtensionAndLargerThan(String extension, long size) {
        // File root = FileSystemView.getFileSystemView().getHomeDirectory();
        File root = new File(System.getProperty(USER_DIR));
        Set<File> commonSet = Collections.synchronizedSet(new HashSet<>());

        try (ForkJoinPool pool = ForkJoinPool.commonPool()) {
            RecursiveFinder finder = new RecursiveFinder(root, AbstractFilter.hasExtension(extension)
                .and(AbstractFilter.largerThan(size)), commonSet);
            pool.invoke(finder);
        }

        return commonSet.stream().toList();
    }

    static final class RecursiveFinder extends RecursiveAction {
        private final File current;
        private final AbstractFilter filter;
        private final Set<File> commonSet;

        RecursiveFinder(File current, AbstractFilter filter, Set<File> commonSet) {
            this.current = current;
            this.filter = filter;
            this.commonSet = commonSet;
        }

        @Override
        protected void compute() {
            List<File> found = new ArrayList<>();
            List<RecursiveFinder> recursiveTasks = new ArrayList<>();

            for (File file : Objects.requireNonNull(current.listFiles())) {
                try {
                    if (filter.accept(file)) {
                        found.add(file);
                    }

                    if (file.isDirectory()) {
                        recursiveTasks.add(new RecursiveFinder(file, filter, commonSet));
                    }
                } catch (IOException e) {
                    LOGGER.info("Exception when processing file " + file.getName());
                }
            }

            commonSet.addAll(found);

            recursiveTasks.forEach(RecursiveAction::fork);
            recursiveTasks.forEach(RecursiveAction::join);
        }
    }

    @FunctionalInterface private interface AbstractFilter extends DirectoryStream.Filter<File> {
        default @NotNull AbstractFilter and(@NotNull AbstractFilter other) {
            return (t) -> accept(t) && other.accept(t);
        }

        static @NotNull AbstractFilter isDirectory() {
            return File::isDirectory;
        }

        static @NotNull AbstractFilter moreThanNFiles(long n) {
            return (t) -> {
                if (!t.isDirectory()) {
                    return false;
                }
                return Objects.requireNonNull(t.listFiles()).length > n;
            };
        }

        static @NotNull AbstractFilter largerThan(long n) {
            return (t) -> t.length() > n;
        }

        static @NotNull AbstractFilter hasExtension(String extension) {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*" + extension);
            return (t) -> {
                try {
                    return matcher.matches(Path.of(t.getName()));
                } catch (Exception any) {
                    return false;
                }
            };
        }
    }
}
