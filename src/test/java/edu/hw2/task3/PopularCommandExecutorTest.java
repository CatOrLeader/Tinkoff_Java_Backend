package edu.hw2.task3;

import edu.hw2.task3.connectionManagers.DefaultConnectionManager;
import edu.hw2.task3.connectionManagers.FaultyConnectionManager;
import edu.hw2.task3.connections.ConnectionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.assertj.core.api.Assertions.assertThat;

public class PopularCommandExecutorTest {
    private static final int MAX_ATTEMPTS = 100;

    @Test
    @DisplayName("Successful execution with DefaultManager")
    void successfulExecutionDefaultManager() {
        PopularCommandExecutor popularCommandExecutor = new PopularCommandExecutor(
            new DefaultConnectionManager(), MAX_ATTEMPTS);

        Assertions.assertDoesNotThrow(popularCommandExecutor::updatePackages);
    }

    @Test
    @DisplayName("Unsuccessful execution with DefaultManager")
    void unsuccessfulExecutionDefaultManager() {
        PopularCommandExecutor popularCommandExecutor = new PopularCommandExecutor(
            new DefaultConnectionManager(), 1);

        Throwable actualValue = null;
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try {
                popularCommandExecutor.updatePackages();
            } catch (ConnectionException exception) {
                actualValue = exception;
                break;
            }
        }

        assertThat(actualValue).isInstanceOf(ConnectionException.class);
    }

    @Test
    @DisplayName("Unsuccessful execution with FaultyManager")
    void unsuccessfulExecutionFaultyManager() {
        PopularCommandExecutor popularCommandExecutor = new PopularCommandExecutor(
            new FaultyConnectionManager(), 1);

        Throwable actualValue = null;
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try {
                popularCommandExecutor.updatePackages();
            } catch (ConnectionException exception) {
                actualValue = exception;
                break;
            }
        }

        assertThat(actualValue).isInstanceOf(ConnectionException.class);
    }
}
