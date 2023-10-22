package edu.hw2.task3;

import edu.hw2.task3.connectionManagers.ConnectionManager;
import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.ConnectionException;

public class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("sudo -rm rf :)");
    }

    void tryExecute(String command) {
        int currentCount = 0;
        Throwable cause = null;
        while (currentCount < maxAttempts) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command);
                return;
            } catch (Exception exception) {
                currentCount++;
                cause = exception;
            }
        }
        throw new ConnectionException(cause);
    }
}
