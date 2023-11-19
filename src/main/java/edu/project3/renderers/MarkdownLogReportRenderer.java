package edu.project3.renderers;

import edu.project3.Configuration;
import edu.project3.LogReport;
import edu.project3.Metrics;
import edu.project3.types.HttpRequestType;
import org.jetbrains.annotations.NotNull;

public final class MarkdownLogReportRenderer implements LogReportRenderer {
    @SuppressWarnings("checkstyle:MultipleStringLiterals") @Override
    public String render(@NotNull Configuration configuration, @NotNull LogReport report) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("#### Overall Information\n");
        stringBuilder.append("| Metric | Value |\n");
        stringBuilder.append("|---------|----------|\n");

        if (LogReportRenderer.isUri(configuration.path())) {
            stringBuilder.append("| URI |").append(configuration.path()).append(" | \n");
        } else {
            stringBuilder.append("| FILES OF GLOB |").append(configuration.path()).append("\n");
        }

        stringBuilder.append("| Date from | ")
            .append(configuration.from() == null ? " --- " : configuration.from()).append(" |\n");
        stringBuilder.append("| Date to | ")
            .append(configuration.to() == null ? " --- " : configuration.to()).append(" |\n");

        stringBuilder.append("| Requests count | ").append(report.requests).append(" |\n");
        stringBuilder.append("| Mean answer size | ").append(report.meanBytes).append(" |\n");
        stringBuilder.append("| Overall bytes sent | ").append(report.bytesSent).append(" |\n");

        stringBuilder.append("\n#### Requested resources\n");
        stringBuilder.append("| Resource | Amount |\n");
        stringBuilder.append("|--------|------------|\n");
        for (String res : report.resources) {
            stringBuilder.append("| ").append(res).append(" | ").append(Metrics.POPULAR_RESOURCES.get(res))
                .append(" |\n");
        }

        stringBuilder.append("\n#### Users\n");
        stringBuilder.append("| User | Count |\n");
        stringBuilder.append("|--------|------------|\n");
        for (String res : report.users) {
            stringBuilder.append("| ").append(res).append(" | ").append(Metrics.POPULAR_USER.get(res))
                .append(" |\n");
        }

        stringBuilder.append("\n#### Protocols \n");
        stringBuilder.append("| Protocol | Amount |\n");
        stringBuilder.append("|--------|------------|\n");
        for (String res : report.protocols) {
            stringBuilder.append("| ").append(res).append(" | ").append(Metrics.POPULAR_PROTOCOL.get(res))
                .append(" |\n");
        }

        stringBuilder.append("\n#### Requests\n");
        stringBuilder.append("| Request | Amount |\n");
        stringBuilder.append("|--------|------------|\n");
        for (HttpRequestType res : report.requests) {
            stringBuilder.append("| ").append(res.name()).append(" | ").append(Metrics.POPULAR_REQUESTS.get(res))
                .append(" |\n");
        }

        stringBuilder.append("\n#### Statuses \n");
        stringBuilder.append("| Status | Amount |\n");
        stringBuilder.append("|--------|------------|\n");
        for (Integer res : report.statuses) {
            stringBuilder.append("| ").append(res).append(" | ").append(Metrics.POPULAR_STATUS.get(res))
                .append(" |\n");
        }

        stringBuilder.append("\n#### Referrers \n");
        stringBuilder.append("| Referrer | Amount |\n");
        stringBuilder.append("|--------|------------|\n");
        for (String res : report.referrers) {
            stringBuilder.append("| ").append(res).append(" | ").append(Metrics.POPULAR_HTTP_REFERER.get(res))
                .append(" |\n");
        }

        stringBuilder.append("\n#### Agents \n");
        stringBuilder.append("| Agent | Amount |\n");
        stringBuilder.append("|--------|------------|\n");
        for (String res : report.agents) {
            stringBuilder.append("| ").append(res).append(" | ").append(Metrics.POPULAR_HTTP_AGENT.get(res))
                .append(" |\n");
        }

        return stringBuilder.toString();
    }
}
