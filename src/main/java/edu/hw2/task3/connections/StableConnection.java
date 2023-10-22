package edu.hw2.task3.connections;

import static edu.hw2.task3.Utils.logger;

public class StableConnection implements Connection {
    @Override
    public void execute(String command) {
        logger.info("Executed command " + command);
    }

    @Override
    public void close() {
        logger.info("Stable connection closed...");
    }
}
