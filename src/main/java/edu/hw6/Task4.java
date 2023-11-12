package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;
import org.jetbrains.annotations.NotNull;

public final class Task4 {
    private Task4() {
    }

    @SuppressWarnings("checkstyle:NestedTryDepth")
    public static void writeToFile(@NotNull String string, @NotNull Path path) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(path)) {
            byte[] msgBytes = string.getBytes(StandardCharsets.UTF_8);
            Checksum checksum = new Adler32();
            checksum.update(msgBytes);

            try (CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, checksum)) {
                try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream)) {
                    try (OutputStreamWriter bufferedWriter = new OutputStreamWriter(
                        bufferedOutputStream,
                        StandardCharsets.UTF_8
                    )) {
                        try (PrintWriter writer = new PrintWriter(bufferedWriter)) {
                            writer.println(string);
                        }
                    }
                }
            }
        }
    }
}
