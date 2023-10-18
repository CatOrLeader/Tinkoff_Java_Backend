package edu.hw2.task3;

import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.StableConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.assertj.core.api.Assertions.assertThat;

public class StableConnectionTest {
    private Connection connection;

    @BeforeEach
    public void setUp() {
        connection = new StableConnection();
    }

    @AfterEach
    public void clean() throws Exception {
        connection.close();
    }

    @Test
    @DisplayName("Connection is established")
    void executeConnection() throws Exception {
        String command = "docker-compose up";

        String actualValue = tapSystemOut(
            () -> connection.execute(command)
        ).trim().split("\u001B")[3].substring(4);
        String expectedValue = "Executed command " + command;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
