package edu.hw2.task4;

public final class Caller {
    private Caller() {
    }

    public static CallingInfo callingInfo() {
        StackTraceElement[] elements = new Throwable().fillInStackTrace().getStackTrace();
        StackTraceElement currentCaller = elements[1];
        return new CallingInfo(currentCaller.getClassName(), currentCaller.getMethodName());
    }
}
