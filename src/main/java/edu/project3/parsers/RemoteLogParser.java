package edu.project3.parsers;

import edu.project3.Configuration;
import edu.project3.LogRecord;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public final class RemoteLogParser implements LogParser {
    @Override
    public Stream<LogRecord> parse(@NotNull Configuration configuration) {
        URI uri = URI.create(configuration.path());
        String[] logs = getBody(uri).orElseThrow(() -> new IllegalArgumentException("Incorrect uri provided"))
            .split("\r?\n|\r");

        List<LogRecord> records = new ArrayList<>();
        for (String logStr : logs) {
            LogRecord log = LogParser.parseLog(logStr);

            if (LogParser.isInvalid(configuration, log)) {
                continue;
            }

            records.add(log);
        }

        return records.stream();
    }

    private static Optional<String> getBody(URI url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(url).GET().build();

            var response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            return Optional.of(response.body());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
