package edu.project3.parsers;

import edu.project3.Configuration;
import edu.project3.types.OutputFormat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.URI;

public class RemoteLogParserTest {
    @Test
    @DisplayName("Incorrect URI provided")
    void incorrectURI() {
        URI uri = URI.create("https://foo:url");
        Configuration configuration = new Configuration(uri.toString(),
            null, null, OutputFormat.MARKDOWN);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new RemoteLogParser().parse(configuration))
            .withMessage("Incorrect uri provided");
    }
}
