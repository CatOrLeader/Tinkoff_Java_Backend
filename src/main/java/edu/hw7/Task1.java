package edu.hw7;

import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

public final class Task1 {
    private Task1() {
    }

    public static void increment(@NotNull AtomicInteger num, int countOfThreads, int finalNumber)
        throws InterruptedException {
        if (finalNumber <= num.get()) {
            throw new IllegalArgumentException("Incorrect initial and final numbers provided");
        }

        if (countOfThreads < 1) {
            throw new IllegalArgumentException("Incorrect amount of threads provided");
        }

        int threadWorkAmount = finalNumber / countOfThreads;
        boolean isDivisible = finalNumber % countOfThreads == 0;

        Thread[] threads = new Thread[countOfThreads];

        for (int i = 0; i < countOfThreads; i++) {
            if (i == countOfThreads - 1 && !isDivisible) {
                threads[i] = new Counter(num, threadWorkAmount + finalNumber % countOfThreads);
                threads[i].start();
                break;
            }

            threads[i] = new Counter(num, threadWorkAmount);
            threads[i].start();
        }

        for (int i = 0; i < countOfThreads; i++) {
            threads[i].join();
        }
    }

    private static final class Counter extends Thread {
        private final AtomicInteger num;
        private final int amountOfIncrements;

        Counter(AtomicInteger num, int amountOfIncrements) {
            this.num = num;
            this.amountOfIncrements = amountOfIncrements;
        }

        public void run() {
            for (int i = 0; i < amountOfIncrements; i++) {
                num.incrementAndGet();
            }
        }
    }
}
