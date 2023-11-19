package edu.project3;

import edu.project3.types.HttpRequestType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class Metrics {
    private Metrics() {
    }

    public static long requestCount = 0;
    public static final List<String> UNPARSED_LOGS = new ArrayList<>();
    public static final Map<String, Integer> POPULAR_ADDRESSES = new HashMap<>();
    public static final Map<String, Integer> POPULAR_USER = new HashMap<>();
    public static final Map<HttpRequestType, Integer> POPULAR_REQUESTS = new HashMap<>();
    public static final Map<String, Integer> POPULAR_RESOURCES = new HashMap<>();
    public static final Map<String, Integer> POPULAR_PROTOCOL = new HashMap<>();
    public static final Map<Integer, Integer> POPULAR_STATUS = new HashMap<>();
    public static long overallBytesSent = 0;
    public static final Map<String, Integer> POPULAR_HTTP_REFERER = new HashMap<>();
    public static final Map<String, Integer> POPULAR_HTTP_AGENT = new HashMap<>();

    public static void collect(@NotNull Configuration configuration, @NotNull LogRecord log) {
        requestCount++;
        fillPopularAddresses(log);
        fillPopularUser(log);
        fillPopularRequest(log);
        fillPopularResource(log);
        fillPopularProtocol(log);
        fillPopularStatus(log);
        fillOverallBytes(log);
        fillPopularReferer(log);
        fillPopularAgent(log);
    }

    public static long getMeanBytes() {
        return overallBytesSent / requestCount;
    }

    private static void fillPopularAddresses(LogRecord log) {
        if (!POPULAR_ADDRESSES.containsKey(log.getAddress())) {
            POPULAR_ADDRESSES.put(log.getAddress(), 0);
        }

        POPULAR_ADDRESSES.replace(log.getAddress(), POPULAR_ADDRESSES.get(log.getAddress()) + 1);
    }

    private static void fillPopularUser(LogRecord log) {
        if (!POPULAR_USER.containsKey(log.getUser())) {
            POPULAR_USER.put(log.getUser(), 0);
        }

        POPULAR_USER.replace(log.getUser(), POPULAR_USER.get(log.getUser()) + 1);
    }

    private static void fillPopularRequest(LogRecord log) {
        if (!POPULAR_REQUESTS.containsKey(log.getRequest())) {
            POPULAR_REQUESTS.put(log.getRequest(), 0);
        }

        POPULAR_REQUESTS.put(log.getRequest(), POPULAR_REQUESTS.get(log.getRequest()) + 1);
    }

    private static void fillPopularResource(LogRecord log) {
        if (!POPULAR_RESOURCES.containsKey(log.getResource())) {
            POPULAR_RESOURCES.put(log.getResource(), 0);
        }

        POPULAR_RESOURCES.replace(log.getResource(), POPULAR_RESOURCES.get(log.getResource()) + 1);
    }

    private static void fillPopularProtocol(LogRecord log) {
        if (!POPULAR_PROTOCOL.containsKey(log.getProtocol())) {
            POPULAR_PROTOCOL.put(log.getProtocol(), 0);
        }

        POPULAR_PROTOCOL.replace(log.getProtocol(), POPULAR_PROTOCOL.get(log.getProtocol()) + 1);
    }

    private static void fillPopularStatus(LogRecord log) {
        if (!POPULAR_STATUS.containsKey(log.getStatus())) {
            POPULAR_STATUS.put(log.getStatus(), 0);
        }

        POPULAR_STATUS.replace(log.getStatus(), POPULAR_STATUS.get(log.getStatus()) + 1);
    }

    private static void fillOverallBytes(LogRecord log) {
        overallBytesSent += log.getBodyBytesSent();
    }

    private static void fillPopularReferer(LogRecord log) {
        if (!POPULAR_HTTP_REFERER.containsKey(log.getHttpReferer())) {
            POPULAR_HTTP_REFERER.put(log.getHttpReferer(), 0);
        }

        POPULAR_HTTP_REFERER.replace(log.getHttpReferer(), POPULAR_HTTP_REFERER.get(log.getHttpReferer()) + 1);
    }

    private static void fillPopularAgent(LogRecord log) {
        if (!POPULAR_HTTP_AGENT.containsKey(log.getHttpUserAgent())) {
            POPULAR_HTTP_AGENT.put(log.getHttpUserAgent(), 0);
        }

        POPULAR_HTTP_AGENT.replace(log.getHttpUserAgent(), POPULAR_HTTP_AGENT.get(log.getHttpUserAgent()) + 1);
    }
}
