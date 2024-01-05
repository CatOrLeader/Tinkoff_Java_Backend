package edu.project3;

import edu.project3.types.HttpRequestType;
import edu.project3.types.OutputFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogReportTest {
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
        LocalDate date = LocalDate.now();
        Configuration configuration = new Configuration("path", date, date, OutputFormat.MARKDOWN);
        LogReport report = new LogReport(configuration);

        // Я оставляю тут такой набор ассершнов, потому что я не понимаю, как разбить их по умному на юниты.
        // Можно написать тест на каждую характеристику, но мне сейчас уже очень лень (В тесте метрик такая же ситуация)
        assertThat(report.getSource()).isEqualTo("path");
        assertThat(report.getFrom()).isEqualTo(date);
        assertThat(report.getTo()).isEqualTo(date);
        assertThat(report.getMeanAnswerSize()).isEqualTo(0);
        assertThat(report.getRequestCount()).isEqualTo(3);
        assertThat(report.getAddresses().getFirst().getKey().equals("a")
                   && report.getAddresses().getFirst().getValue().equals(3)
                   && report.getAddresses().size() == 1).isTrue();
        assertThat(report.getUsers().getFirst().getKey().equals("User")
                   && report.getUsers().getFirst().getValue().equals(3)
                   && report.getUsers().size() == 1).isTrue();
        assertThat(report.getRequests().getFirst().getKey().equals(HttpRequestType.GET)
                   && report.getRequests().getFirst().getValue().equals(3)
                   && report.getRequests().size() == 1).isTrue();
        assertThat(report.getResources().getFirst().getKey().equals("Resource")
                   && report.getResources().getFirst().getValue().equals(3)
                   && report.getResources().size() == 1).isTrue();
        assertThat(report.getProtocols().getFirst().getKey().equals("Protocol")
                   && report.getProtocols().getFirst().getValue().equals(3)
                   && report.getProtocols().size() == 1).isTrue();
        assertThat(report.getStatuses().getFirst().getKey().equals(200)
                   && report.getStatuses().getFirst().getValue().equals(3)
                   && report.getStatuses().size() == 1).isTrue();
        assertThat(report.getOverallBytesSent() == 0).isTrue();
        assertThat(report.getReferrers().getFirst().getKey().equals("Referer")
                   && report.getReferrers().getFirst().getValue().equals(3)
                   && report.getReferrers().size() == 1).isTrue();
        assertThat(report.getAgents().getFirst().getKey().equals("Agent")
                   && report.getAgents().getFirst().getValue().equals(3)
                   && report.getAgents().size() == 1).isTrue();
    }
}
