package edu.hw7;

import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

public final class Task4 {
    private Task4() {
    }

    private static final String THROWABLE_MSG = "Incorrect n provided";

    private static final double MULTIPLIER = 4;
    private static final int LOWER_BORDER = 0;
    private static final int UPPER_BORDER = 1;

    private static double getPoint() {
        return RandomGenerator.getDefault().nextDouble(LOWER_BORDER, UPPER_BORDER);
    }

    private static boolean isInCircle(double x, double y) {
        return Math.pow(x, 2) + Math.pow(y, 2) <= UPPER_BORDER;
    }

    public static double calculatePiSingleThread(int n) {
        if (n < 1) {
            throw new IllegalArgumentException(THROWABLE_MSG);
        }

        long totalCount = 0;
        long circleCount = 0;

        for (int i = 0; i < n; i++) {
            double x = getPoint();
            double y = getPoint();

            if (isInCircle(x, y)) {
                circleCount++;
            }
            totalCount++;
        }

        return MULTIPLIER * ((double) circleCount / (double) totalCount);
    }

    public static double calculatePiMultiThreads(int n, int threadsCount) throws InterruptedException {
        if (n < 1 || threadsCount < 1) {
            throw new IllegalArgumentException(THROWABLE_MSG);
        }

        long totalCount = 0;
        long circleCount = 0;

        Calculator[] calculators = startAll(n, threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            calculators[i].join();
        }

        for (int i = 0; i < threadsCount; i++) {
            totalCount += calculators[i].getTotalCount();
            circleCount += calculators[i].getCircleCount();
        }

        return MULTIPLIER * ((double) circleCount / (double) totalCount);
    }

    private static Calculator[] startAll(
        int n, int threadsCount
    ) {
        int threadWorkAmount = n / threadsCount;
        boolean isDivisible = n % threadsCount == 0;

        Calculator[] threads = new Calculator[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            if (i == threadsCount - 1 && !isDivisible) {
                threads[i] = new Calculator(threadWorkAmount + n % threadsCount);
                threads[i].start();
                break;
            }

            threads[i] = new Calculator(threadWorkAmount);
            threads[i].start();
        }

        return threads;
    }

    private static final class Calculator extends Thread {
        private long totalCount;
        private long circleCount;
        private final int amountOfIter;

        Calculator(int amountOfIter) {
            this.totalCount = 0;
            this.circleCount = 0;
            this.amountOfIter = amountOfIter;
        }

        public void run() {
            for (int i = 0; i < amountOfIter; i++) {
                double x = getPointThreadSafeRandom();
                double y = getPointThreadSafeRandom();

                if (isInCircle(x, y)) {
                    circleCount++;
                }
                totalCount++;
            }
        }

        private static double getPointThreadSafeRandom() {
            return ThreadLocalRandom.current().nextDouble(LOWER_BORDER, UPPER_BORDER);
        }

        public long getTotalCount() {
            return totalCount;
        }

        public long getCircleCount() {
            return circleCount;
        }
    }
}
