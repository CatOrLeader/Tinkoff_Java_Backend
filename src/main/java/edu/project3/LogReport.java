package edu.project3;

import edu.project3.types.HttpRequestType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class LogReport {
    private static final int NEEDED_ELEM_COUNT = 3;

    // Common information
    private final String source;
    private final LocalDate from;
    private final LocalDate to;
    private final long requestCount;
    private final long overallBytesSent;
    private final long meanAnswerSize;

    private final List<Map.Entry<String, Integer>> addresses = new ArrayList<>();
    private final List<Map.Entry<String, Integer>> users = new ArrayList<>();
    private final List<Map.Entry<HttpRequestType, Integer>> requests = new ArrayList<>();
    private final List<Map.Entry<String, Integer>> resources = new ArrayList<>();
    private final List<Map.Entry<String, Integer>> protocols = new ArrayList<>();
    private final List<Map.Entry<Integer, Integer>> statuses = new ArrayList<>();
    private final List<Map.Entry<String, Integer>> referrers = new ArrayList<>();
    private final List<Map.Entry<String, Integer>> agents = new ArrayList<>();

    public LogReport(@NotNull Configuration configuration) {
        this.source = configuration.path();
        this.from = configuration.from();
        this.to = configuration.to();
        this.requestCount = Metrics.requestCount;
        this.overallBytesSent = Metrics.overallBytesSent;
        this.meanAnswerSize = Metrics.getMeanBytes();

        extractPopularAddresses();
        extractPopularUsers();
        extractPopularRequests();
        extractPopularResources();
        extractPopularProtocols();
        extractPopularStatuses();
        extractPopularReferrers();
        extractPopularAgents();
    }

    private void extractPopularAddresses() {
        var temp = Metrics.POPULAR_ADDRESSES.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();

        for (int i = 0; i < Math.min(temp.size(), NEEDED_ELEM_COUNT); i++) {
            addresses.add(temp.get(i));
        }
    }

    private void extractPopularUsers() {
        var temp = Metrics.POPULAR_USERS.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();

        for (int i = 0; i < Math.min(temp.size(), NEEDED_ELEM_COUNT); i++) {
            users.add(temp.get(i));
        }
    }

    private void extractPopularRequests() {
        var temp = Metrics.POPULAR_REQUESTS.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();

        for (int i = 0; i < Math.min(temp.size(), NEEDED_ELEM_COUNT); i++) {
            requests.add(temp.get(i));
        }
    }

    private void extractPopularResources() {
        var temp = Metrics.POPULAR_RESOURCES.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();

        for (int i = 0; i < Math.min(temp.size(), NEEDED_ELEM_COUNT); i++) {
            resources.add(temp.get(i));
        }
    }

    private void extractPopularProtocols() {
        var temp = Metrics.POPULAR_PROTOCOLS.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();

        for (int i = 0; i < Math.min(temp.size(), NEEDED_ELEM_COUNT); i++) {
            protocols.add(temp.get(i));
        }
    }

    private void extractPopularStatuses() {
        var temp = Metrics.POPULAR_STATUSES.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();

        for (int i = 0; i < Math.min(temp.size(), NEEDED_ELEM_COUNT); i++) {
            statuses.add(temp.get(i));
        }
    }

    private void extractPopularReferrers() {
        var temp = Metrics.POPULAR_HTTP_REFERRERS.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();

        for (int i = 0; i < Math.min(temp.size(), NEEDED_ELEM_COUNT); i++) {
            referrers.add(temp.get(i));
        }
    }

    private void extractPopularAgents() {
        var temp = Metrics.POPULAR_HTTP_AGENTS.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();

        for (int i = 0; i < Math.min(temp.size(), NEEDED_ELEM_COUNT); i++) {
            agents.add(temp.get(i));
        }
    }

    public String getSource() {
        return source;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public long getOverallBytesSent() {
        return overallBytesSent;
    }

    public long getMeanAnswerSize() {
        return meanAnswerSize;
    }

    public List<Map.Entry<String, Integer>> getAddresses() {
        return addresses;
    }

    public List<Map.Entry<String, Integer>> getUsers() {
        return users;
    }

    public List<Map.Entry<HttpRequestType, Integer>> getRequests() {
        return requests;
    }

    public List<Map.Entry<String, Integer>> getResources() {
        return resources;
    }

    public List<Map.Entry<String, Integer>> getProtocols() {
        return protocols;
    }

    public List<Map.Entry<Integer, Integer>> getStatuses() {
        return statuses;
    }

    public List<Map.Entry<String, Integer>> getReferrers() {
        return referrers;
    }

    public List<Map.Entry<String, Integer>> getAgents() {
        return agents;
    }
}
