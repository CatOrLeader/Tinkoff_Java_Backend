package edu.project3;

import edu.project3.types.HttpRequestType;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MetricsTest {
    private static final LogRecord RECORD = LogRecord.Builder.newInstance()
        .setUser("User")
        .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
        .setRequest(HttpRequestType.GET)
        .setResource("Resource")
        .setProtocol("Protocol")
        .setStatus(200)
        .setAddress("a")
        .setBodyBytesSent(0)
        .setHttpReferer("Referer")
        .setHttpUserAgent("Agent").build();

    @Test
    @DisplayName("Correct assembling")
    void correctAssembling() {
        Metrics.clear();
        for (int i = 0; i < 3; i++) {
            Metrics.collect(RECORD);
        }

        assertThat(Metrics.getMeanBytes()).isEqualTo(0);
        assertThat(Metrics.requestCount).isEqualTo(3);
        assertThat(Metrics.POPULAR_ADDRESSES.get("a").equals(3) && Metrics.POPULAR_ADDRESSES.size() == 1).isTrue();
        assertThat(Metrics.POPULAR_USERS.get("User").equals(3) && Metrics.POPULAR_USERS.size() == 1).isTrue();
        assertThat(Metrics.POPULAR_REQUESTS.get(HttpRequestType.GET).equals(3) &&
                   Metrics.POPULAR_REQUESTS.size() == 1).isTrue();
        assertThat(
            Metrics.POPULAR_RESOURCES.get("Resource").equals(3) && Metrics.POPULAR_RESOURCES.size() == 1).isTrue();
        assertThat(
            Metrics.POPULAR_PROTOCOLS.get("Protocol").equals(3) && Metrics.POPULAR_PROTOCOLS.size() == 1).isTrue();
        assertThat(Metrics.POPULAR_STATUSES.get(200).equals(3) && Metrics.POPULAR_STATUSES.size() == 1).isTrue();
        assertThat(Metrics.overallBytesSent == 0).isTrue();
        assertThat(Metrics.POPULAR_HTTP_REFERRERS.get("Referer").equals(3) &&
                   Metrics.POPULAR_HTTP_REFERRERS.size() == 1).isTrue();
        assertThat(
            Metrics.POPULAR_HTTP_AGENTS.get("Agent").equals(3) && Metrics.POPULAR_HTTP_AGENTS.size() == 1).isTrue();
    }
}
