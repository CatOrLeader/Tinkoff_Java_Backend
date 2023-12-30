package edu.project3.parsers;

import edu.project3.Configuration;
import edu.project3.LogRecord;
import edu.project3.types.HttpRequestType;
import edu.project3.types.OutputFormat;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
public class RemoteLogParserTest {
    private static final String LOG_STRING =
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";
    private static final LogRecord LOG = LogRecord.Builder.newInstance()
        .setAddress("93.180.71.3")
        .setUser("-")
        .setTime(OffsetDateTime.of(2015, 5, 17, 8, 5, 32, 0, ZoneOffset.UTC))
        .setRequest(HttpRequestType.GET)
        .setResource("/downloads/product_1")
        .setProtocol("HTTP/1.1")
        .setStatus(304)
        .setBodyBytesSent(0)
        .setHttpReferer("-")
        .setHttpUserAgent("Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)")
        .build();

    private static final LogParser PARSER = new RemoteLogParser();
    private final ClientAndServer server;

    public RemoteLogParserTest(ClientAndServer server) {
        this.server = server;
        this.server.when(
            request()
                .withMethod("GET")
                .withPath("/logs")
        ).respond(
            response()
                .withStatusCode(200)
                .withBody(LOG_STRING + "\n" + LOG_STRING)
        );
    }

    @Test
    @DisplayName("Incorrect URI provided")
    void incorrectURI() {
        URI uri = URI.create("https://foo:url");
        Configuration configuration = new Configuration(uri.toString(),
            null, null, OutputFormat.MARKDOWN
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> PARSER.parse(configuration))
            .withMessage("Incorrect uri provided");
    }

    @Test
    @DisplayName("Correct URI provided")
    void correctRequest() {
        URI uri = URI.create("http://localhost:" + server.getPort() + "/logs");
        Configuration configuration = new Configuration(uri.toString(),
            null, null, OutputFormat.MARKDOWN
        );

        List<LogRecord> actualRecords = PARSER.parse(configuration).toList();
        List<LogRecord> expectedRecords = List.of(LOG, LOG);

        assertThat(actualRecords).containsExactlyInAnyOrderElementsOf(expectedRecords);
    }
}
