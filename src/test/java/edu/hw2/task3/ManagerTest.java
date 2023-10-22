package edu.hw2.task3;

import edu.hw2.task3.connectionManagers.ConnectionManager;
import edu.hw2.task3.connectionManagers.DefaultConnectionManager;
import edu.hw2.task3.connectionManagers.FaultyConnectionManager;
import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.FaultyConnection;
import edu.hw2.task3.connections.StableConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ManagerTest {
    private static final int MAX_ATTEMPTS = 100;

    @Test
    @DisplayName("DefaultConnectionManager returns StableConnection")
    void defaultReturnStable() {
        ConnectionManager connectionManager = new DefaultConnectionManager();

        Connection actualValue = null;
        for (int i = 0; i < MAX_ATTEMPTS; i++){
            actualValue = connectionManager.getConnection();
            if (actualValue instanceof StableConnection) break;
        }

        assertThat(actualValue).isExactlyInstanceOf(StableConnection.class);
    }

    @Test
    @DisplayName("DefaultConnectionManager returns FaultyConnection")
    void defaultReturnFaulty() {
        ConnectionManager connectionManager = new DefaultConnectionManager();

        Connection actualValue = null;
        for (int i = 0; i < MAX_ATTEMPTS; i++){
            actualValue = connectionManager.getConnection();
            if (actualValue instanceof FaultyConnection) break;
        }

        assertThat(actualValue).isExactlyInstanceOf(FaultyConnection.class);
    }

    @Test
    @DisplayName("FaultyConnectionManager returns only FaultyConnections")
    void faultyReturnFaulty() {
        ConnectionManager connectionManager = new FaultyConnectionManager();

        Connection actualValue = null;
        for (int i = 0; i < MAX_ATTEMPTS; i++){
            Connection currentValue = connectionManager.getConnection();
            if (!(currentValue instanceof FaultyConnection)) {
                break;
            }
        }

        assertThat(actualValue).isNull();
    }
}
