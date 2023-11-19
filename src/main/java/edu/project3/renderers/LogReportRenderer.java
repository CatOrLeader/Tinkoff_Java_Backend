package edu.project3.renderers;

import edu.project3.Configuration;
import edu.project3.LogReport;
import org.jetbrains.annotations.NotNull;

public sealed interface LogReportRenderer permits AdocLogReportRenderer, MarkdownLogReportRenderer {
    String render(@NotNull Configuration configuration, @NotNull LogReport report);

    static boolean isUri(@NotNull String path) {
        return path.matches("http.*://.*");
    }
}
