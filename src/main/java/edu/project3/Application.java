package edu.project3;

import edu.project3.parsers.CommandLineArgumentsParser;
import edu.project3.parsers.FileLogParser;
import edu.project3.parsers.LogParser;
import edu.project3.parsers.RemoteLogParser;
import edu.project3.renderers.AdocLogReportRenderer;
import edu.project3.renderers.LogReportRenderer;
import edu.project3.renderers.MarkdownLogReportRenderer;
import edu.project3.types.OutputFormat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.apache.commons.cli.ParseException;

public final class Application {
    private Application() {
    }

    public static void main(String[] args) throws ParseException, IOException {
        Configuration configuration = prepareConfiguration(args);

        LogParser parser = prepareParser(configuration);
        Stream<LogRecord> records = parser.parse(configuration);

        records.forEach(Metrics::collect);
        LogReportRenderer reportRenderer = prepareRenderer(configuration);
        LogReport report = new LogReport(configuration);

        String reportString = reportRenderer.render(report);
        writeToFile(configuration, reportString);
    }

    private static Configuration prepareConfiguration(String[] args) throws ParseException {
        CommandLineArgumentsParser parser = new CommandLineArgumentsParser(args);
        return new Configuration(parser.parsePath(), parser.parseFrom(), parser.parseTo(), parser.parseFormat());
    }

    private static LogParser prepareParser(Configuration configuration) {
        if (configuration.path().matches("http.*://.*")) {
            return new RemoteLogParser();
        } else {
            return new FileLogParser();
        }
    }

    private static LogReportRenderer prepareRenderer(Configuration configuration) {
        if (configuration.outputFormat().equals(OutputFormat.MARKDOWN)) {
            return new MarkdownLogReportRenderer();
        } else {
            return new AdocLogReportRenderer();
        }
    }

    private static void writeToFile(Configuration configuration, String report) throws IOException {
        Files.write(
            Paths.get(
                System.getProperty("user.dir"),
                configuration.outputFormat().equals(OutputFormat.MARKDOWN)
                    ? "report.md"
                    : "report.adoc"
            ), report.getBytes()
        );
    }
}
