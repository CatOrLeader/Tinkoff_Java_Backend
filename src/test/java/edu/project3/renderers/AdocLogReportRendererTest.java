package edu.project3.renderers;

import edu.project3.Configuration;
import edu.project3.LogRecord;
import edu.project3.LogReport;
import edu.project3.Metrics;
import edu.project3.types.HttpRequestType;
import edu.project3.types.OutputFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AdocLogReportRendererTest {
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
    @DisplayName("Correct rendering with least information")
    void correctRendering() {
        Metrics.clear();
        for (int i = 0; i < 3; i++) {
            Metrics.collect(RECORD);
        }
        LocalDate date = LocalDate.of(2023, 12, 31);
        Configuration configuration = new Configuration("path", date, date, OutputFormat.MARKDOWN);
        LogReportRenderer reportRenderer = new AdocLogReportRenderer();
        LogReport report = new LogReport(configuration);

        String actualReportString = reportRenderer.render(report);

        assertThat(actualReportString).containsIgnoringWhitespaces(EXPECTED_REPORT_STRING);
    }

    private static final String EXPECTED_REPORT_STRING = """
        .Общая информация
        |===
        |Метрика |Значение\s

        |Ресурс (-ы)
        |path

        |Начальная дата
        |2023-12-31

        |Конечная дата
        |2023-12-31

        |Количество запросов
        |3

        |Общий размер ответов
        |0

        |Средний размер ответа
        |0

        |===



        .Частые адреса
        |===
        |Адрес |Количество\s

        |a
        |3

        |===



        .Пользователи
        |===
        |Пользователь |Количество\s

        |User
        |3

        |===



        .Частые запросы
        |===
        |Запрос |Количество\s

        |GET
        |3

        |===



        .Запрашиваемые ресурсы
        |===
        |Ресурс |Количество\s

        |Resource
        |3

        |===



        .Частые протоколы
        |===
        |Протокол |Количество\s

        |Protocol
        |3

        |===



        .Частые статусы
        |===
        |Статус |Количество\s

        |200
        |3

        |===



        .Частые ссылающие
        |===
        |Ссылающий |Количество\s

        |Referer
        |3

        |===



        .Частые агенты
        |===
        |Агент |Количество\s

        |Agent
        |3

        |===


        """;
}
