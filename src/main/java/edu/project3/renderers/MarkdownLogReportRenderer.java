package edu.project3.renderers;

import edu.project3.LogReport;
import edu.project3.types.HttpRequestType;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import static edu.project3.renderers.TableRendererUtils.COUNT_STR;
import static edu.project3.renderers.TableRendererUtils.ROW_SEP;
import static edu.project3.renderers.TableRendererUtils.TYPICAL_BLANK_OFFSET_LENGTH;

public final class MarkdownLogReportRenderer implements LogReportRenderer {
    @Override public String render(@NotNull LogReport report) {
        return makeCommonInfoTable(report)
               + ROW_SEP + makeAddressesTable(report.getAddresses())
               + ROW_SEP + makeUsersTable(report.getUsers())
               + ROW_SEP + makeRequestsTable(report.getRequests())
               + ROW_SEP + makeResourcesTable(report.getResources())
               + ROW_SEP + makeProtocolsTable(report.getProtocols())
               + ROW_SEP + makeStatusesTable(report.getStatuses())
               + ROW_SEP + makeReferrersTable(report.getReferrers())
               + ROW_SEP + makeAgentsTable(report.getAgents());
    }

    private static int getRemainder(int stringLength) {
        return TYPICAL_BLANK_OFFSET_LENGTH - stringLength;
    }

    private static String stringWithBlankOffsetWithoutLastSep(String string) {
        return "| " + string + " ".repeat(getRemainder(string.length()));
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private static String createTableHeader(String tableName, String... strings) {
        if (strings.length == 0) {
            throw new IllegalArgumentException("Incorrect headers provided");
        }

        StringBuilder headers = new StringBuilder();
        StringBuilder separators = new StringBuilder();
        headers.append("#### ").append(tableName).append(ROW_SEP);
        for (String string : strings) {
            headers.append("|").append(" ").append(string).append(" ".repeat(getRemainder(string.length())));
            separators.append("|").append(":").append("-".repeat(getRemainder(1))).append(":");
        }
        headers.append("|\n");
        separators.append("|\n");

        return headers.append(separators).toString();
    }

    private static String makeCommonInfoTable(LogReport report) {
        String resources = "Ресурс (-ы)";
        String resourcesRow =
            stringWithBlankOffsetWithoutLastSep(resources)
            + stringWithBlankOffsetWithoutLastSep(report.getSource())
            + "|\n";

        String from = "Начальная дата";
        String fromRow = stringWithBlankOffsetWithoutLastSep(from)
                         + stringWithBlankOffsetWithoutLastSep(
            report.getFrom() != null ? report.getFrom().format(DateTimeFormatter.ISO_LOCAL_DATE) : "-")
                         + "|\n";

        String to = "Конечная дата";
        String toRow = stringWithBlankOffsetWithoutLastSep(to)
                       + stringWithBlankOffsetWithoutLastSep(
            report.getTo() != null ? report.getTo().format(DateTimeFormatter.ISO_LOCAL_DATE) : "-")
                       + "|\n";

        String requestCount = "Количество запросов";
        String requestCountRow = stringWithBlankOffsetWithoutLastSep(requestCount)
                                 + stringWithBlankOffsetWithoutLastSep(String.valueOf(report.getRequestCount()))
                                 + "|\n";

        String overallBytesSent = "Общий размер ответов";
        String overallBytesSentRow = stringWithBlankOffsetWithoutLastSep(overallBytesSent)
                                     + stringWithBlankOffsetWithoutLastSep(String.valueOf(report.getOverallBytesSent()))
                                     + "|\n";

        String meanAnswerSize = "Средний размер ответа";
        String meanAnswerSizeRow = stringWithBlankOffsetWithoutLastSep(meanAnswerSize)
                                   + stringWithBlankOffsetWithoutLastSep(String.valueOf(report.getMeanAnswerSize()))
                                   + "|\n";

        return createTableHeader("Общая информация", "Метрика", "Значение")
               + resourcesRow
               + fromRow
               + toRow
               + requestCountRow
               + overallBytesSentRow
               + meanAnswerSizeRow;
    }

    private static String makeAddressesTable(List<Map.Entry<String, Integer>> addresses) {
        StringBuilder builder = new StringBuilder();

        for (var entry : addresses) {
            builder.append(stringWithBlankOffsetWithoutLastSep(entry.getKey()))
                .append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getValue())))
                .append("|\n");
        }

        return createTableHeader("Частые адреса", "Адрес", COUNT_STR)
               + builder;
    }

    private static String makeUsersTable(List<Map.Entry<String, Integer>> users) {
        StringBuilder builder = new StringBuilder();

        for (var entry : users) {
            builder.append(stringWithBlankOffsetWithoutLastSep(entry.getKey()))
                .append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getValue())))
                .append("|\n");
        }

        return createTableHeader("Пользователи", "Пользователь", COUNT_STR)
               + builder;
    }

    private static String makeRequestsTable(List<Map.Entry<HttpRequestType, Integer>> requests) {
        StringBuilder builder = new StringBuilder();

        for (var entry : requests) {
            builder.append(stringWithBlankOffsetWithoutLastSep(entry.getKey().name()))
                .append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getValue())))
                .append("|\n");
        }

        return createTableHeader("Частые запросы", "Запрос", COUNT_STR)
               + builder;
    }

    private static String makeResourcesTable(List<Map.Entry<String, Integer>> resources) {
        StringBuilder builder = new StringBuilder();

        for (var entry : resources) {
            builder.append(stringWithBlankOffsetWithoutLastSep(entry.getKey()))
                .append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getValue())))
                .append("|\n");
        }

        return createTableHeader("Запрашиваемые ресурсы", "Ресурс", COUNT_STR)
               + builder;
    }

    private static String makeProtocolsTable(List<Map.Entry<String, Integer>> protocols) {
        StringBuilder builder = new StringBuilder();

        for (var entry : protocols) {
            builder.append(stringWithBlankOffsetWithoutLastSep(entry.getKey()))
                .append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getValue())))
                .append("|\n");
        }

        return createTableHeader("Частые протоколы", "Протокол", COUNT_STR)
               + builder;
    }

    private static String makeStatusesTable(List<Map.Entry<Integer, Integer>> statuses) {
        StringBuilder builder = new StringBuilder();

        for (var entry : statuses) {
            builder.append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getKey())))
                .append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getValue())))
                .append("|\n");
        }

        return createTableHeader("Частые статусы", "Статус", COUNT_STR)
               + builder;
    }

    private static String makeReferrersTable(List<Map.Entry<String, Integer>> referrers) {
        StringBuilder builder = new StringBuilder();

        for (var entry : referrers) {
            builder.append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getKey())))
                .append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getValue())))
                .append("|\n");
        }

        return createTableHeader("Частые ссылающие", "Ссылающий", COUNT_STR)
               + builder;
    }

    private static String makeAgentsTable(List<Map.Entry<String, Integer>> agents) {
        StringBuilder builder = new StringBuilder();

        for (var entry : agents) {
            builder.append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getKey())))
                .append(stringWithBlankOffsetWithoutLastSep(String.valueOf(entry.getValue())))
                .append("|\n");
        }

        return createTableHeader("Частые агенты", "Агент", COUNT_STR)
               + builder;
    }
}
