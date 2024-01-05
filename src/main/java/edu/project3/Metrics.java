package edu.project3;

import edu.project3.types.HttpRequestType;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class Metrics {
    private Metrics() {
    }

    public static long requestCount = 0;
    public static final Map<String, Integer> POPULAR_ADDRESSES = new HashMap<>();
    public static final Map<String, Integer> POPULAR_USERS = new HashMap<>();
    public static final Map<HttpRequestType, Integer> POPULAR_REQUESTS = new HashMap<>();
    public static final Map<String, Integer> POPULAR_RESOURCES = new HashMap<>();
    public static final Map<String, Integer> POPULAR_PROTOCOLS = new HashMap<>();
    public static final Map<Integer, Integer> POPULAR_STATUSES = new HashMap<>();
    public static long overallBytesSent = 0;
    public static final Map<String, Integer> POPULAR_HTTP_REFERRERS = new HashMap<>();
    public static final Map<String, Integer> POPULAR_HTTP_AGENTS = new HashMap<>();

    public static void collect(@NotNull LogRecord log) {
        requestCount++;
        fillPopularAddress(log);
        fillPopularUser(log);
        fillPopularRequest(log);
        fillPopularResource(log);
        fillPopularProtocol(log);
        fillPopularStatus(log);
        fillOverallBytes(log);
        fillPopularReferer(log);
        fillPopularAgent(log);
    }

    public static void clear() {
        requestCount = 0;
        POPULAR_ADDRESSES.clear();
        POPULAR_USERS.clear();
        POPULAR_REQUESTS.clear();
        POPULAR_RESOURCES.clear();
        POPULAR_PROTOCOLS.clear();
        POPULAR_STATUSES.clear();
        overallBytesSent = 0;
        POPULAR_HTTP_REFERRERS.clear();
        POPULAR_HTTP_AGENTS.clear();
    }

    public static long getMeanBytes() {
        return overallBytesSent / requestCount;
    }

    private static void fillPopularAddress(LogRecord log) {
        String address = log.getAddress();

        if (!POPULAR_ADDRESSES.containsKey(address)) {
            POPULAR_ADDRESSES.put(address, 0);
        }

        POPULAR_ADDRESSES.replace(address, POPULAR_ADDRESSES.get(address) + 1);
    }

    private static void fillPopularUser(LogRecord log) {
        String user = log.getUser();

        if (!POPULAR_USERS.containsKey(user)) {
            POPULAR_USERS.put(user, 0);
        }

        POPULAR_USERS.replace(user, POPULAR_USERS.get(user) + 1);
    }

    private static void fillPopularRequest(LogRecord log) {
        HttpRequestType type = log.getRequest();

        if (!POPULAR_REQUESTS.containsKey(type)) {
            POPULAR_REQUESTS.put(type, 0);
        }

        POPULAR_REQUESTS.put(type, POPULAR_REQUESTS.get(type) + 1);
    }

    private static void fillPopularResource(LogRecord log) {
        String resource = log.getResource();

        if (!POPULAR_RESOURCES.containsKey(resource)) {
            POPULAR_RESOURCES.put(resource, 0);
        }

        POPULAR_RESOURCES.replace(resource, POPULAR_RESOURCES.get(resource) + 1);
    }

    private static void fillPopularProtocol(LogRecord log) {
        String protocol = log.getProtocol();

        if (!POPULAR_PROTOCOLS.containsKey(protocol)) {
            POPULAR_PROTOCOLS.put(protocol, 0);
        }

        POPULAR_PROTOCOLS.replace(protocol, POPULAR_PROTOCOLS.get(protocol) + 1);
    }

    private static void fillPopularStatus(LogRecord log) {
        int status = log.getStatus();

        if (!POPULAR_STATUSES.containsKey(status)) {
            POPULAR_STATUSES.put(status, 0);
        }

        POPULAR_STATUSES.replace(status, POPULAR_STATUSES.get(status) + 1);
    }

    private static void fillOverallBytes(LogRecord log) {
        overallBytesSent += log.getBodyBytesSent();
    }

    private static void fillPopularReferer(LogRecord log) {
        String referer = log.getHttpReferer();

        if (!POPULAR_HTTP_REFERRERS.containsKey(referer)) {
            POPULAR_HTTP_REFERRERS.put(referer, 0);
        }

        POPULAR_HTTP_REFERRERS.replace(referer, POPULAR_HTTP_REFERRERS.get(referer) + 1);
    }

    private static void fillPopularAgent(LogRecord log) {
        String agent = log.getHttpUserAgent();

        if (!POPULAR_HTTP_AGENTS.containsKey(agent)) {
            POPULAR_HTTP_AGENTS.put(agent, 0);
        }

        POPULAR_HTTP_AGENTS.replace(agent, POPULAR_HTTP_AGENTS.get(agent) + 1);
    }
}
