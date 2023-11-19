package edu.project3;

import edu.project3.types.HttpRequestType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class LogReport {
    private static final int ELEMENTS_COUNT = 3;

    public long logsCount;
    public final List<String> addresses = new ArrayList<>();
    public final List<String> users = new ArrayList<>();
    public final List<HttpRequestType> requests = new ArrayList<>();
    public final List<String> resources = new ArrayList<>();
    public final List<String> protocols = new ArrayList<>();
    public final List<Integer> statuses = new ArrayList<>();
    public long bytesSent;
    public final List<String> referrers = new ArrayList<>();
    public final List<String> agents = new ArrayList<>();
    public long meanBytes;

    public LogReport() {
        logsCount = Metrics.requestCount;

        var addressesFull = Metrics.POPULAR_ADDRESSES.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();
        var usersFull = Metrics.POPULAR_USER.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();
        var requestsFull = Metrics.POPULAR_REQUESTS.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();
        var resourcesFull = Metrics.POPULAR_RESOURCES.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();
        var protocolsFull = Metrics.POPULAR_PROTOCOL.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();
        var statusesFull = Metrics.POPULAR_STATUS.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();
        var referrersFull = Metrics.POPULAR_HTTP_REFERER.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();
        var agentsFull = Metrics.POPULAR_HTTP_AGENT.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)
        ).toList().reversed();

        logsCount = Metrics.requestCount;
        bytesSent = Metrics.overallBytesSent;
        meanBytes = Metrics.getMeanBytes();
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            if (addressesFull.size() > i) {
                addresses.add(addressesFull.get(i).getKey());
            }

            if (usersFull.size() > i) {
                users.add(usersFull.get(i).getKey());
            }

            if (requestsFull.size() > i) {
                requests.add(requestsFull.get(i).getKey());
            }

            if (resourcesFull.size() > i) {
                resources.add(resourcesFull.get(i).getKey());
            }

            if (protocolsFull.size() > i) {
                protocols.add(protocolsFull.get(i).getKey());
            }

            if (statusesFull.size() > i) {
                statuses.add(statusesFull.get(i).getKey());
            }

            if (referrersFull.size() > i) {
                referrers.add(referrersFull.get(i).getKey());
            }

            if (agentsFull.size() > i) {
                agents.add(agentsFull.get(i).getKey());
            }
        }
    }
}
