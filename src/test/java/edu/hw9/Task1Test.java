package edu.hw9;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw9.Task1.StatsCollector;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task1Test {
    @Test
    @DisplayName("Empty array provided")
    void emptyArray() {
        double[] data = new double[] {};
        StatsCollector collector = new StatsCollector();

        assertThatExceptionOfType(ArrayIndexOutOfBoundsException.class)
            .isThrownBy(() -> collector.push("Array 1", data))
            .withMessage("Empty array provided");
    }

    @Test
    @DisplayName("Single thread with several arrays")
    void singleThread_SeveralArrays() {
        StatsCollector collector = new StatsCollector();
        List<double[]> sources = List.of(
            new double[] {1},
            new double[] {1, 2, 3},
            new double[] {-1, 1}
        );
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            executorService.execute(() -> {
                collector.push("Single", sources.get(0));
                collector.push("Default", sources.get(1));
                collector.push("Negative", sources.get(2));
            });
        }
        List<StatsCollector.Metric> actualMetrics = collector.stats();

        List<Double> sum = List.of(1.0, 6.0, 0.0);
        List<Double> avg = List.of(1.0, 2.0, 0.0);
        List<Double> max = List.of(1.0, 3.0, 1.0);
        List<Double> min = List.of(1.0, 1.0, -1.0);

        int index = 0;
        for (var metric : actualMetrics) {
            assertThat(metric.getSum()).isEqualTo(sum.get(index));
            assertThat(metric.getAvg()).isEqualTo(avg.get(index));
            assertThat(metric.getMax()).isEqualTo(max.get(index));
            assertThat(metric.getMin()).isEqualTo(min.get(index));

            index++;
        }
    }

    @Test
    @DisplayName("Several threads with several arrays")
    void severalThreads_SeveralArrays() {
        StatsCollector collector = new StatsCollector();
        List<double[]> sources = List.of(
            new double[] {1},
            new double[] {1, 2, 3},
            new double[] {-1, 1}
        );
        List<String> names = List.of("Single", "Default", "Negative");
        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
            for (int i = 0; i < 3; i++) {
                final int index = i;
                executorService.execute(() -> collector.push(names.get(index), sources.get(index)));
            }
        }
        List<StatsCollector.Metric> actualMetrics = collector.stats();

        List<Double> sum = List.of(1.0, 6.0, 0.0);
        List<Double> avg = List.of(1.0, 2.0, 0.0);
        List<Double> max = List.of(1.0, 3.0, 1.0);
        List<Double> min = List.of(1.0, 1.0, -1.0);

        for (var metric : actualMetrics) {
            int index = names.indexOf(metric.getName());

            assertThat(metric.getSum()).isEqualTo(sum.get(index));
            assertThat(metric.getAvg()).isEqualTo(avg.get(index));
            assertThat(metric.getMax()).isEqualTo(max.get(index));
            assertThat(metric.getMin()).isEqualTo(min.get(index));
        }
    }
}
