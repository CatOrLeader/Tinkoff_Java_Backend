package edu.hw2.task3.connectionManagers;

import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.FaultyConnection;
import edu.hw2.task3.connections.StableConnection;
import java.util.Random;

public class DefaultConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() {
        int mockRandom = new Random().nextInt() % 2;
        return (mockRandom == 1 ? new FaultyConnection() : new StableConnection());
    }
}
