package edu.hw2.task4;

public record CallingInfo(String className, String methodName) {
    @Override
    public String toString() {
        return "This function is called from class <" + className + ">, from the method <" + methodName + ">";
    }
}
