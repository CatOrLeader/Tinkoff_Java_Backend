package edu.project3.renderers;

import edu.project3.LogReport;
import edu.project3.types.HttpRequestType;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import static edu.project3.renderers.TableRendererUtils.ASCII_TABLE_SEP;
import static edu.project3.renderers.TableRendererUtils.COUNT_STR;
import static edu.project3.renderers.TableRendererUtils.ROW_SEP;

public final class AdocLogReportRenderer implements LogReportRenderer {
    @Override
    public String render(@NotNull LogReport report) {
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

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private static String createTableHeader(String tableName, String... strings) {
        if (strings.length == 0) {
            throw new IllegalArgumentException("Incorrect headers provided");
        }

        StringBuilder headers = new StringBuilder();
        headers.append(".").append(tableName).append("\n").append(ASCII_TABLE_SEP).append("\n");
        for (String string : strings) {
            headers.append("|").append(string).append(" ");
        }
        headers.append(ROW_SEP);

        return headers.toString();
    }

    private static String makeCommonInfoTable(LogReport report) {
        String resourcesRow = "|Ресурс (-ы)\n|" + report.getSource() + ROW_SEP;
        String fromRow = "|Начальная дата\n|"
                         + (report.getFrom() != null ? report.getFrom().format(DateTimeFormatter.ISO_LOCAL_DATE) : "-")
                         + ROW_SEP;
        String toRow = "|Конечная дата\n|"
                       + (report.getTo() != null ? report.getTo().format(DateTimeFormatter.ISO_LOCAL_DATE) : "-")
                       + ROW_SEP;
        String requestCountRow = "|Количество запросов\n|" + report.getRequestCount() + ROW_SEP;
        String overallBytesSentRow = "|Общий размер ответов\n|" + report.getOverallBytesSent() + ROW_SEP;
        String meanAnswerSizeRow = "|Средний размер ответа\n|" + report.getMeanAnswerSize() + ROW_SEP;

        return createTableHeader("Общая информация", "Метрика", "Значение")
               + resourcesRow
               + fromRow
               + toRow
               + requestCountRow
               + overallBytesSentRow
               + meanAnswerSizeRow
               + ASCII_TABLE_SEP + ROW_SEP;
    }

    private static String makeAddressesTable(List<Map.Entry<String, Integer>> addresses) {
        StringBuilder builder = new StringBuilder();

        for (var entry : addresses) {
            builder.append("|").append(entry.getKey()).append("\n")
                .append("|").append(entry.getValue()).append(ROW_SEP);
        }

        return createTableHeader("Частые адреса", "Адрес", COUNT_STR)
               + builder + ASCII_TABLE_SEP + ROW_SEP;
    }

    private static String makeUsersTable(List<Map.Entry<String, Integer>> users) {
        StringBuilder builder = new StringBuilder();

        for (var entry : users) {
            builder.append("|").append(entry.getKey()).append("\n")
                .append("|").append(entry.getValue()).append(ROW_SEP);
        }

        return createTableHeader("Пользователи", "Пользователь", COUNT_STR)
               + builder + ASCII_TABLE_SEP + ROW_SEP;
    }

    private static String makeRequestsTable(List<Map.Entry<HttpRequestType, Integer>> requests) {
        StringBuilder builder = new StringBuilder();

        for (var entry : requests) {
            builder.append("|").append(entry.getKey().name()).append("\n")
                .append("|").append(entry.getValue()).append(ROW_SEP);
        }

        return createTableHeader("Частые запросы", "Запрос", COUNT_STR)
               + builder + ASCII_TABLE_SEP + ROW_SEP;
    }

    private static String makeResourcesTable(List<Map.Entry<String, Integer>> resources) {
        StringBuilder builder = new StringBuilder();

        for (var entry : resources) {
            builder.append("|").append(entry.getKey()).append("\n")
                .append("|").append(entry.getValue()).append(ROW_SEP);
        }

        return createTableHeader("Запрашиваемые ресурсы", "Ресурс", COUNT_STR)
               + builder + ASCII_TABLE_SEP + ROW_SEP;
    }

    private static String makeProtocolsTable(List<Map.Entry<String, Integer>> protocols) {
        StringBuilder builder = new StringBuilder();

        for (var entry : protocols) {
            builder.append("|").append(entry.getKey()).append("\n")
                .append("|").append(entry.getValue()).append(ROW_SEP);
        }

        return createTableHeader("Частые протоколы", "Протокол", COUNT_STR)
               + builder + ASCII_TABLE_SEP + ROW_SEP;
    }

    private static String makeStatusesTable(List<Map.Entry<Integer, Integer>> statuses) {
        StringBuilder builder = new StringBuilder();

        for (var entry : statuses) {
            builder.append("|").append(entry.getKey()).append("\n")
                .append("|").append(entry.getValue()).append(ROW_SEP);
        }

        return createTableHeader("Частые статусы", "Статус", COUNT_STR)
               + builder + ASCII_TABLE_SEP + ROW_SEP;
    }

    private static String makeReferrersTable(List<Map.Entry<String, Integer>> referrers) {
        StringBuilder builder = new StringBuilder();

        for (var entry : referrers) {
            builder.append("|").append(entry.getKey()).append("\n")
                .append("|").append(entry.getValue()).append(ROW_SEP);
        }

        return createTableHeader("Частые ссылающие", "Ссылающий", COUNT_STR)
               + builder + ASCII_TABLE_SEP + ROW_SEP;
    }

    private static String makeAgentsTable(List<Map.Entry<String, Integer>> agents) {
        StringBuilder builder = new StringBuilder();

        for (var entry : agents) {
            builder.append("|").append(entry.getKey()).append("\n")
                .append("|").append(entry.getValue()).append(ROW_SEP);
        }

        return createTableHeader("Частые агенты", "Агент", COUNT_STR)
               + builder + ASCII_TABLE_SEP + ROW_SEP;
    }
}
