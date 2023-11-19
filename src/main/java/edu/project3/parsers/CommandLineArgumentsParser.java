package edu.project3.parsers;

import edu.project3.OptionsProvider;
import edu.project3.types.OutputFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CommandLineArgumentsParser {
    private final CommandLine cmd;

    public CommandLineArgumentsParser(@NotNull String[] args) throws ParseException {
        cmd = new DefaultParser().parse(OptionsProvider.options(), args);
    }

    public @NotNull String parsePath() {
        String option = cmd.getOptionValue(OptionsProvider.PATH_OPTION);
        if (option == null) {
            throw new IllegalArgumentException("Incorrect path provided");
        }
        return option;
    }

    public @Nullable LocalDate parseFrom() {
        String value = cmd.getOptionValue(OptionsProvider.FROM_OPTION);
        if (value == null) {
            return null;
        } else {
            return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public @Nullable LocalDate parseTo() {
        String value = cmd.getOptionValue(OptionsProvider.TO_OPTION);
        if (value == null) {
            return null;
        } else {
            return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public @NotNull OutputFormat parseFormat() {
        return OutputFormat.parseValue(cmd.getOptionValue(OptionsProvider.FORMAT_OPTION, "markdown"));
    }
}
