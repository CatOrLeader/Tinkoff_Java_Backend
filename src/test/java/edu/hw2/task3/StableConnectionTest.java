package edu.hw2.task3;

import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.StableConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StableConnectionTest {
    private static final int MAX_ATTEMPTS = 100;

    private Connection connection;

    @BeforeEach
    public void setUp() {
        connection = new StableConnection();
    }

    @Test
    @DisplayName("Connection is established")
    void executeConnection() throws Exception {
        String command = "docker-compose up";

        Throwable actualValue = null;
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try {
                connection.execute(command);
                connection.close();
            } catch (Exception any) {
                actualValue = any;
            }
        }

        assertThat(actualValue).isNull();
    }
}
