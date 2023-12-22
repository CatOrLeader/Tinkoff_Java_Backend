package edu.project5;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class Task1Test {
    @State(Scope.Thread)
    public static class ReflectionBenchmark {
        public static void main(String[] args) throws RunnerException {
            Options options = new OptionsBuilder()
                .include(ReflectionBenchmark.class.getSimpleName())
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .forks(1)
                .warmupForks(1)
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(5))
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(5))
                .build();

            new Runner(options).run();
        }

        private static final String methodName = "name";

        record Student(String name, String surname) {
        }

        interface Interface {
            String name(Student student);
        }

        private Student student;
        private Method method;
        private MethodHandle methodHandle;
        private Interface anInterface;

        @Setup
        public void setup() throws Throwable {
            student = new Student("Artur", "Mukhutdinov");
            method = Student.class.getMethod(methodName);
            methodHandle =
                MethodHandles.lookup().findVirtual(Student.class, methodName, MethodType.methodType(String.class));
            anInterface = (Interface) LambdaMetafactory.metafactory(
                MethodHandles.lookup(),
                methodName,
                MethodType.methodType(Interface.class),
                MethodType.methodType(String.class, Student.class),
                methodHandle,
                MethodType.methodType(String.class, Student.class)
            ).getTarget().invokeExact();
        }

        @Benchmark
        public void directAccess(Blackhole bh) {
            String name = student.name();
            bh.consume(name);
        }

        @Benchmark
        public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
            String name = (String) method.invoke(student);
            bh.consume(name);
        }

        @Benchmark
        public void methodHandle(Blackhole bh) throws Throwable {
            String name = (String) methodHandle.invoke(student);
            bh.consume(name);
        }

        @Benchmark
        public void lambdaMetaFactory(Blackhole bh) throws Throwable {
            String name = anInterface.name(student);
            bh.consume(name);
        }
    }
}
