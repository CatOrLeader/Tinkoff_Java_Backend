package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public final class Task3 {
    private Task3() {
    }

    @FunctionalInterface public interface AbstractFilter extends DirectoryStream.Filter<Path> {
        default @NotNull AbstractFilter and(@NotNull AbstractFilter other) {
            return (t) -> {
                try {
                    return accept(t) && other.accept(t);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
        }

        default @NotNull AbstractFilter or(@NotNull AbstractFilter other) {
            return (t) -> {
                try {
                    return accept(t) || other.accept(t);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
        }

        static @NotNull AbstractFilter largerThan(long value) {
            return (t) -> t.toFile().length() > value;
        }

        static @NotNull AbstractFilter globMatches(@NotNull String glob) {
            return (t) -> t.getFileName().toString().substring(t.getFileName().toString().lastIndexOf("."))
                .equals(glob.substring(glob.lastIndexOf(".")));
        }

        static @NotNull AbstractFilter regexContains(@NotNull String regexp) {
            return (t) -> t.getFileName().toString().matches(regexp);
        }

        static @NotNull AbstractFilter magicNumber(int... args) {
            return (t) -> {
                try {
                    byte[] fileBytes = Files.readAllBytes(t);

                    if (fileBytes.length < args.length) {
                        return false;
                    }

                    int index = 0;
                    for (int i : args) {
                        if (i != fileBytes[index++]) {
                            return false;
                        }
                    }

                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
        }
    }
}
