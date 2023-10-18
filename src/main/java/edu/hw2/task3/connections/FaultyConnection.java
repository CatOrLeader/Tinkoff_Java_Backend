package edu.hw2.task3.connections;

import java.util.Random;
import static edu.hw2.task3.Utils.logger;

public class FaultyConnection implements Connection {
    private final int mockRandom = new Random().nextInt() % 2;

    @Override
    public void execute(String command) {
        if (mockRandom == 1) {
            throw new ConnectionException(new Throwable("Faulty connection get dropped..."));
        }
        logger.info("Executed command " + command);
    }

    @Override
    public void close() {
        if (mockRandom == 1) {
            throw new ConnectionException(new Throwable("Cannot close unestablished connection"));
        }
    }
}
