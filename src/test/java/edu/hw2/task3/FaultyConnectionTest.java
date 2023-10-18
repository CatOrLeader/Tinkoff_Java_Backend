package edu.hw2.task3;

import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.ConnectionException;
import edu.hw2.task3.connections.FaultyConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.assertj.core.api.Assertions.assertThat;

public class FaultyConnectionTest {
    private static final int MAX_ATTEMPTS = 100;
    private static final String closedExceptionCause = "Cannot close unestablished connection";
    private static final String executionExceptionCause = "Faulty connection get dropped...";

    @Test
    @DisplayName("Connection is not established")
    @Order(1)
    void executeConnectionFailed() {
        String command = "docker-compose up";

        Throwable actualValue = null;
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try {
                Connection connection = new FaultyConnection();
                connection.execute(command);
                connection.close();
            } catch (ConnectionException exception) {
                actualValue = exception;
                break;
            } catch (Exception ignored) {
            }
        }
        Throwable expectedValue = new Throwable(executionExceptionCause);

        assertThat(actualValue).hasCause(expectedValue);
    }

    @Test
    @DisplayName("Close established connection")
    @Order(2)
    void closeEstablishedConnection() {
        String command = "docker-compose up";

        Throwable actualValue = null;
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try {
                Connection connection = new FaultyConnection();
                connection.execute(command);
                connection.close();
                actualValue = null;
                break;
            } catch (ConnectionException exception) {
                actualValue = exception;
            } catch (Exception ignored) {
            }
        }

        assertThat(actualValue).isNull();
    }

    @Test
    @DisplayName("Close failed connection")
    @Order(3)
    void closeFailedConnection() {
        Throwable actualValue = null;
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try {
                Connection connection = new FaultyConnection();
                connection.close();
            } catch (ConnectionException exception) {
                actualValue = exception;
                break;
            } catch (Exception ignored) {
            }
        }
        Throwable expectedValue = new Throwable(closedExceptionCause);

        assertThat(actualValue).hasCause(expectedValue);
    }
}
