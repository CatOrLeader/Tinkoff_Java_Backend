package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

public final class Task5 {
    private Task5() {
    }

    public static class HackerNews {
        private static final URI ROOT_LINK = URI.create("https://hacker-news.firebaseio.com/v0");
        private static final URI IDS_LINK = URI.create("/topstories.json");
        private static final URI ITEM_LINK = URI.create("/item");

        private static final Pattern TITLE_PATTERN = Pattern.compile("\"title\":\"(.*?)\"");

        public static @NotNull Optional<String> news(long id) {
            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder(URI.create(ROOT_LINK + ITEM_LINK.toString() + "/"
                                                                        + id + ".json"))
                    .GET().build();

                var response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
                );

                return parseTitle(response.body());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Parsed info is incorrect | No such id");
            }
        }

        private static Optional<String> parseTitle(String requestBody) {
            Matcher matcher = TITLE_PATTERN.matcher(requestBody);
            if (matcher.find()) {
                return Optional.of(matcher.group(1));
            } else {
                return Optional.empty();
            }
        }

        public static long[] hackerNewsTopStories() {
            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder(URI.create(ROOT_LINK + IDS_LINK.toString()))
                    .GET().build();

                var response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
                );

                return parseArray(response.body());
            } catch (IOException | InterruptedException e) {
                LogManager.getLogger().info("Parsed info is incorrect");
                return new long[0];
            }
        }

        private static long[] parseArray(String requestBody) {
            List<String> strings = List.of(requestBody.substring(1, requestBody.length() - 1).split(","));
            return strings.stream()
                .mapToLong(Long::parseLong).toArray();
        }
    }
}
