package edu.project3.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OutputFormatTest {
    @Test
    @DisplayName("Incorrect value provided")
    void incorrectValue() {
        String value = "sudo rm -rf \\";

        OutputFormat expectedOutputFormat = OutputFormat.MARKDOWN;
        OutputFormat actualOutputFormat = OutputFormat.parseValue(value);

        assertThat(actualOutputFormat).isEqualTo(expectedOutputFormat);
    }

    @Test
    @DisplayName("Markdown value provided")
    void markdownValue() {
        String value = "markdown";

        OutputFormat expectedOutputFormat = OutputFormat.MARKDOWN;
        OutputFormat actualOutputFormat = OutputFormat.parseValue(value);

        assertThat(actualOutputFormat).isEqualTo(expectedOutputFormat);
    }

    @Test
    @DisplayName("Adoc value provided")
    void adocValue() {
        String value = "adoc";

        OutputFormat expectedOutputFormat = OutputFormat.ADOC;
        OutputFormat actualOutputFormat = OutputFormat.parseValue(value);

        assertThat(actualOutputFormat).isEqualTo(expectedOutputFormat);
    }
}
