package edu.project3;

import edu.project3.types.OutputFormat;
import java.time.LocalDate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Configuration(@NotNull String path, @Nullable LocalDate from,
                            @Nullable LocalDate to, @NotNull OutputFormat outputFormat) {
}
