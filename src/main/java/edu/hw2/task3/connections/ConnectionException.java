package edu.hw2.task3.connections;

public class ConnectionException extends RuntimeException {
    private final Throwable cause;

    public ConnectionException(Throwable cause) {
        this.cause = cause;
    }

    @Override public Throwable getCause() {
        return cause;
    }
}
