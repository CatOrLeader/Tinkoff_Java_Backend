package edu.hw8;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw8.Task1.Client;
import static edu.hw8.Task1.Server;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    private static final int THREADS_THRESHOLD = 5;
    private static final String LOCALHOST = "127.0.0.1";
    private static final int PORT = 8080;

    private static Server SERVER;

    @BeforeAll
    static void setup() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                SERVER = new Server(THREADS_THRESHOLD, PORT);
                SERVER.start();
            } catch (IOException | ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        Thread.sleep(1000);
    }

    @AfterAll
    static void shutdown() throws IOException {
        SERVER.stop();
    }

    @Test
    @DisplayName("Single thread, all type of messages")
    void singleThread_CorrectMessages() throws IOException {
        String[] messages = new String[] {"личности", "оскорбления", "глупый", "интеллект", "incorrect"};
        Client client = new Client(LOCALHOST, PORT);

        List<String> actualAnswers = new ArrayList<>();
        for (String message : messages) {
            actualAnswers.add(client.sendAndGet(message));
        }

        List<String> expectedAnswers = List.of(
            "Не переходи на личности там, где их нет",
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
            "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
            "Чем ниже интеллект, тем громче оскорбления",
            "Сервер не может ответить"
        );

        assertThat(actualAnswers).containsExactlyInAnyOrderElementsOf(expectedAnswers);
    }

    @Test
    @DisplayName("More than threshold threads, all type of messages")
    void moreThanThresholdThreads_CorrectMessages() {
        int currentThreadsNumber = 100;
        String message = "личности";
        List<String> actualAnswers = Collections.synchronizedList(new ArrayList<>());
        try (ExecutorService service = Executors.newFixedThreadPool(currentThreadsNumber)) {
            for (int i = 0; i < currentThreadsNumber; i++) {
                service.execute(() -> {
                    try {
                        Client client = new Client(LOCALHOST, PORT);
                        actualAnswers.add(client.sendAndGet(message));
                        client.disconnect();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        List<String> expectedAnswers = new ArrayList<>();
        for (int i = 0; i < currentThreadsNumber; i++) {
            expectedAnswers.add("Не переходи на личности там, где их нет");
        }

        assertThat(actualAnswers).containsExactlyInAnyOrderElementsOf(expectedAnswers);
    }
}
