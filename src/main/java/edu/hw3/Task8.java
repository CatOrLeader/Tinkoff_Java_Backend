package edu.hw3;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

public final class Task8 {
    private Task8() {
    }

    public static class BackwardIterator<T> implements Iterator<T> {
        private final Collection<T> collection;
        private int position;

        public BackwardIterator(@NotNull Collection<T> collection) {
            this.collection = collection;
            this.position = this.collection.size();
        }

        @Override
        public boolean hasNext() {
            return position > 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more elements in the iterator");
            }

            Iterator<T> iterator = collection.iterator();

            int elementIndex = 0;
            while (elementIndex++ != position - 1) {
                iterator.next();
            }

            position--;
            return iterator.next();
        }
    }
}
