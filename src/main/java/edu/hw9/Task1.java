package edu.hw9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;

public final class Task1 {
    private Task1() {
    }

    public static final class StatsCollector {
        // Stats collectors
        private final List<Metric> metrics;

        private final Map<String, Double> sum;
        private final Map<String, Double> avg;
        private final Map<String, Double> max;
        private final Map<String, Double> min;

        public StatsCollector() {
            this.metrics = new ArrayList<>();

            this.sum = Collections.synchronizedMap(new HashMap<>());
            this.avg = Collections.synchronizedMap(new HashMap<>());
            this.max = Collections.synchronizedMap(new HashMap<>());
            this.min = Collections.synchronizedMap(new HashMap<>());
        }

        public List<Metric> stats() {
            return metrics;
        }

        public void push(@NotNull String metricName, double @NotNull [] data) {
            if (data.length == 0) {
                throw new ArrayIndexOutOfBoundsException("Empty array provided");
            }

            try (ExecutorService executor = Executors.newThreadPerTaskExecutor(Executors.defaultThreadFactory())) {
                evaluateSum(executor, metricName, data);
                evaluateAvg(executor, metricName, data);
                evaluateMax(executor, metricName, data);
                evaluateMin(executor, metricName, data);
            }

            metrics.add(new Metric(metricName));
        }

        private void evaluateSum(ExecutorService executor, String metricName, double[] data) {
            executor.execute(() -> sum.put(metricName, Arrays.stream(data).sum()));
        }

        private void evaluateAvg(ExecutorService executor, String metricName, double[] data) {
            executor.execute(() -> avg.put(metricName, Arrays.stream(data).sum() / (double) data.length));
        }

        private void evaluateMax(ExecutorService executor, String metricName, double[] data) {
            executor.execute(() -> max.put(metricName, Arrays.stream(data).max().orElseThrow()));
        }

        private void evaluateMin(ExecutorService executor, String metricName, double[] data) {
            executor.execute(() -> min.put(metricName, Arrays.stream(data).min().orElseThrow()));
        }

        public final class Metric {
            private final String name;

            private Metric(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public double getSum() {
                return sum.get(name);
            }

            public double getAvg() {
                return avg.get(name);
            }

            public double getMax() {
                return max.get(name);
            }

            public double getMin() {
                return min.get(name);
            }
        }
    }
}
