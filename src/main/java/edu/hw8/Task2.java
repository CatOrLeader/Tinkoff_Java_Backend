package edu.hw8;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public final class Task2 {
    private Task2() {
    }

    public static final class RejectedExecution implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new RejectedExecutionException("Task "
                                                 + r.toString()
                                                 + " rejected from "
                                                 + FixedThreadPool.class.getSimpleName());
        }
    }

    public interface ThreadPool extends AutoCloseable {
        void start();

        void execute(Runnable runnable);
    }

    public static final class FixedThreadPool implements ThreadPool {
        private volatile boolean isRunning;
        private final int corePoolSize;
        private final Thread[] pool;
        private final BlockingQueue<Runnable> workQueue;
        private final ThreadFactory threadFactory;
        private final RejectedExecutionHandler handler;

        private FixedThreadPool(int corePoolSize) {
            if (corePoolSize <= 0) {
                throw new IllegalArgumentException();
            }

            this.isRunning = true;
            this.corePoolSize = corePoolSize;
            this.pool = new Thread[this.corePoolSize];
            this.workQueue = new LinkedBlockingQueue<>();
            this.threadFactory = Executors.defaultThreadFactory();
            this.handler = new RejectedExecution();
        }

        public static ThreadPool create(int corePoolSize) {
            return new FixedThreadPool(corePoolSize);
        }

        @Override
        public void start() {
            for (int i = 0; i < corePoolSize; i++) {
                pool[i] = new Thread(() -> {
                    while (true) {
                        if (Thread.currentThread().isInterrupted() && workQueue.isEmpty()) {
                            return;
                        }

                        try {
                            workQueue.take().run();
                        } catch (InterruptedException ignored) {
                        }
                    }
                });
                pool[i].start();
            }
        }

        @Override
        public void execute(Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException();
            }

            if (!isRunning) {
                reject(runnable);
            }

            if (isRunning) {
                int count = createdThreads();
                if (count < corePoolSize) {
                    pool[count] = threadFactory.newThread(runnable);
                }
            }

            if (isRunning && workQueue.offer(runnable)) {
                int free = getFree();
                if (!isRunning && workQueue.remove(runnable)) {
                    reject(runnable);
                } else if (free == -1) {
                    workQueue.add(runnable);
                } else {
                    pool[free] = threadFactory.newThread(runnable);
                }
            }
        }

        @Override
        public void close() throws InterruptedException {
            isRunning = false;
            for (int i = 0; i < corePoolSize; i++) {
                pool[i].interrupt();
            }
        }

        private int createdThreads() {
            int count = 0;
            for (Thread thread : pool) {
                if (thread != null) {
                    count++;
                }
            }
            return count;
        }

        private int getFree() {
            for (int i = 0; i < corePoolSize; i++) {
                if (!pool[i].isAlive() || pool[i].isInterrupted()) {
                    return i;
                }
            }
            return -1;
        }

        private void reject(Runnable runnable) {
            handler.rejectedExecution(runnable, null);
        }
    }
}
