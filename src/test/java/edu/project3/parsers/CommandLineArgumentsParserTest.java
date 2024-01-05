package edu.project3.parsers;

import edu.project3.types.OutputFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommandLineArgumentsParserTest {
    @Test
    @DisplayName("Correct path")
    void correctPath() throws ParseException {
        String[] args = new String[] {"--path", "logs"};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        String expectedValue = "logs";
        String actualValue = parser.parsePath();

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("No path value")
    void noPathValue() {
        String[] args = new String[] {"--path"};

        Assertions.assertThrows(MissingArgumentException.class, () -> new CommandLineArgumentsParser(args));
    }

    @Test
    @DisplayName("No path")
    void noPath() throws ParseException {
        String[] args = new String[] {};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        Assertions.assertThrows(IllegalArgumentException.class, parser::parsePath);
    }

    @Test
    @DisplayName("Correct from")
    void correctFrom() throws ParseException {
        String[] args = new String[] {"--from", "2023-08-01"};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        LocalDate expectedValue = LocalDate.of(2023, 8, 1);
        LocalDate actualValue = parser.parseFrom();

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect from")
    void incorrectFrom() throws ParseException {
        String[] args = new String[] {"--from", "2023123"};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        Assertions.assertThrows(DateTimeParseException.class, parser::parseFrom);
    }

    @Test
    @DisplayName("No from value")
    void noFromValue() {
        String[] args = new String[] {"--from"};

        Assertions.assertThrows(MissingArgumentException.class, () -> new CommandLineArgumentsParser(args));
    }

    @Test
    @DisplayName("No from")
    void noFrom() throws ParseException {
        String[] args = new String[] {};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        LocalDate actualValue = parser.parseFrom();

        assertThat(actualValue).isNull();
    }

    @Test
    @DisplayName("Correct to")
    void correctTo() throws ParseException {
        String[] args = new String[] {"--to", "2023-08-01"};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        LocalDate expectedValue = LocalDate.of(2023, 8, 1);
        LocalDate actualValue = parser.parseTo();

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect to")
    void incorrectTo() throws ParseException {
        String[] args = new String[] {"--to", "2023123"};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        Assertions.assertThrows(DateTimeParseException.class, parser::parseTo);
    }

    @Test
    @DisplayName("No to value")
    void noToValue() {
        String[] args = new String[] {"--to"};

        Assertions.assertThrows(MissingArgumentException.class, () -> new CommandLineArgumentsParser(args));
    }

    @Test
    @DisplayName("No to")
    void noTo() throws ParseException {
        String[] args = new String[] {};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        LocalDate actualValue = parser.parseTo();

        assertThat(actualValue).isNull();
    }

    @Test
    @DisplayName("Correct format")
    void correctFormat() throws ParseException {
        String[] args = new String[] {"--format", "adoc"};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        OutputFormat expectedValue = OutputFormat.ADOC;
        OutputFormat actualValue = parser.parseFormat();

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect format")
    void incorrectFormat() throws ParseException {
        String[] args = new String[] {"--format", "sdawffas"};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        OutputFormat expectedValue = OutputFormat.MARKDOWN;
        OutputFormat actualValue = parser.parseFormat();

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("No format value")
    void noFormatValue() {
        String[] args = new String[] {"--format"};

        Assertions.assertThrows(MissingArgumentException.class, () -> new CommandLineArgumentsParser(args));
    }

    @Test
    @DisplayName("No format")
    void noFormat() throws ParseException {
        String[] args = new String[] {};
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);

        OutputFormat expectedValue = OutputFormat.MARKDOWN;
        OutputFormat actualValue = parser.parseFormat();

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
