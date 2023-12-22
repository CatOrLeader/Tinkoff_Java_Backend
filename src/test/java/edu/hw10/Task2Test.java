package edu.hw10;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw10.Task2.Cache;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task2Test {
    private static final Path DIRECTORY = Paths.get("src", "test", "resources", "edu", "hw10", "task2");
    private static final Path STORAGE = DIRECTORY.resolve(Path.of("temp.txt"));

    @BeforeAll
    public static void setup() throws IOException {
        Files.createDirectories(DIRECTORY);
    }

    @AfterEach
    public void tearUp() throws IOException {
        for (File file : Objects.requireNonNull(DIRECTORY.toFile().listFiles())) {
            Files.deleteIfExists(file.toPath());
        }
    }

    public interface FibCalculator {
        @Cache()
        long fibRuntimeMap(int number);

        @Cache(persist = true, onDiskStorage = "src/test/resources/edu/hw10/task2/temp.txt")
        long fibFile(int number);

        @Cache(persist = true, onDiskStorage = "src/test/resources/edu/hw10/task2/")
        int fibIncorrectStorage(int number);

        @Cache()
        double fibUnsupportedType();
    }

    public static final class DefaultFibCalculator implements FibCalculator {
        public DefaultFibCalculator() {
        }

        @Override
        public long fibRuntimeMap(int number) {
            long a = 0;
            long b = 1;
            for (int i = 0; i < number; i++) {
                long temp = a;
                a = b;
                b = temp + b;
            }
            return a;
        }

        @Override
        public long fibFile(int number) {
            long a = 0;
            long b = 1;
            for (int i = 0; i < number; i++) {
                long temp = a;
                a = b;
                b = temp + b;
            }
            return a;
        }

        @Override
        public int fibIncorrectStorage(int number) {
            return 0;
        }

        @Override
        public double fibUnsupportedType() {
            return 0;
        }
    }

    @Test
    @DisplayName("Check correctness of fib calculator")
    void baseCorrectness() {
        FibCalculator calculator = new DefaultFibCalculator();

        final int[] numbers = new int[] {1, 2, 3, 4, 5};
        final long[] answers = new long[] {1, 1, 2, 3, 5};

        for (int i = 0; i < numbers.length; i++) {
            assertThat(calculator.fibRuntimeMap(numbers[i])).isEqualTo(answers[i]);
        }
    }

    @Test
    @DisplayName(
        "Provide incorrect storage")
    void incorrectStorage() {
        final int number = 5;
        DefaultFibCalculator calculator = new DefaultFibCalculator();
        FibCalculator proxy = (FibCalculator) Task2.CacheProxy.create(calculator, calculator.getClass());

        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(() -> proxy.fibIncorrectStorage(number));
    }

    @Test
    @DisplayName(
        "Provide unsupported type")
    void unsupportedType() {
        DefaultFibCalculator calculator = new DefaultFibCalculator();
        FibCalculator proxy = (FibCalculator) Task2.CacheProxy.create(calculator, calculator.getClass());
        proxy.fibUnsupportedType();

        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(proxy::fibUnsupportedType);
    }

    @Test
    @DisplayName(
        "Check if the program works with cache for single value calculation, runtime map contains key and value")
    void singleValueCachedCalculation_runtimeMap() {
        final int number = 5;
        final int answer = 5;
        DefaultFibCalculator calculator = new DefaultFibCalculator();
        FibCalculator proxy = (FibCalculator) Task2.CacheProxy.create(calculator, calculator.getClass());

        assertThat(proxy.fibRuntimeMap(number)).isEqualTo(answer);
        assertThat(proxy.fibRuntimeMap(number)).isEqualTo(answer); // Get value from cache
    }

    @Test
    @DisplayName(
        "Check if the program works with cache for single value calculation, file storage contains key and value")
    void singleValueCachedCalculation_fileStorage() throws IOException {
        final int number = 5;
        final int answer = 5;
        DefaultFibCalculator calculator = new DefaultFibCalculator();
        FibCalculator proxy = (FibCalculator) Task2.CacheProxy.create(calculator, calculator.getClass());

        assertThat(proxy.fibFile(number)).isEqualTo(answer);
        assertThat(proxy.fibFile(number)).isEqualTo(answer); // Get from cache
        assertThat(STORAGE.toFile().exists()).isTrue();
        assertThat(Files.readString(STORAGE).trim()).containsIgnoringNewLines("5-:-5");
    }

    @Test
    @DisplayName(
        "Check if the program works with cache for multiple values calculation, file storage contains key and value")
    void multipleValuesCachedCalculation_fileStorage() throws IOException {
        final int number = 5;
        final int answer = 5;
        DefaultFibCalculator calculator = new DefaultFibCalculator();
        FibCalculator proxy = (FibCalculator) Task2.CacheProxy.create(calculator, calculator.getClass());

        assertThat(proxy.fibFile(number)).isEqualTo(answer);
        assertThat(proxy.fibFile(number)).isEqualTo(answer); // Get from cache
        assertThat(proxy.fibFile(number + 1)).isEqualTo(answer + 3);
        assertThat(STORAGE.toFile().exists()).isTrue();
        assertThat(Files.readString(STORAGE).trim()).containsIgnoringNewLines("5-:-5\n6-:-8");
    }
}
