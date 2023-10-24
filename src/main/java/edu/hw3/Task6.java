package edu.hw3;

import java.util.PriorityQueue;
import java.util.Queue;
import org.jetbrains.annotations.NotNull;

public final class Task6 {
    private Task6() {
    }

    public record Stock(double cost) implements Comparable<Stock> {
        @Override
        public int compareTo(@NotNull Task6.Stock o) {
            return (int) (o.cost - cost);
        }
    }

    public static class StockMarket {
        Queue<Stock> queue = new PriorityQueue<>(Stock::compareTo);

        /** Добавить акцию */
        public void add(Stock stock) {
            queue.add(stock);
        }

        /** Удалить акцию */
        public void remove(Stock stock) {
            queue.remove(stock);
        }

        /**
         * Get the most valuable stock, does not remove it from market
         * @return most valuable stock
         */
        public Stock mostValuableStock() {
            return queue.peek();
        }
    }
}
