package edu.hw3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import static edu.hw3.Task8.BackwardIterator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class Task8Test {
    @Test
    @DisplayName("Simple list of integers")
    void simpleIntegersList() {
        List<Integer> list = List.of(1, 2, 3);
        Iterator<Integer> iterator = new BackwardIterator<>(list);

        List<Integer> actualList = new ArrayList<>();
        while (iterator.hasNext()) {
            actualList.add(iterator.next());
        }
        List<Integer> expectedList = List.of(3, 2, 1);

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @DisplayName("Simple list of string")
    void simpleStringsList() {
        List<String> list = List.of(
            "bbbbbbb", "aaaaaaa", "sudo -rm rf"
        );
        Iterator<String> iterator = new BackwardIterator<>(list);

        List<String> actualList = new ArrayList<>();
        while (iterator.hasNext()) {
            actualList.add(iterator.next());
        }
        List<String> expectedList = List.of(
            "sudo -rm rf", "aaaaaaa", "bbbbbbb"
        );

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @DisplayName("No such element exception")
    void noSuchElementExceptionThrown() {
        List<String> list = List.of(
            "bbbbbbb", "aaaaaaa", "sudo -rm rf"
        );
        Iterator<String> iterator = new BackwardIterator<>(list);

        List<String> actualList = new ArrayList<>();
        while (iterator.hasNext()) {
            actualList.add(iterator.next());
        }

        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("No elements provided")
    void noElementsProvided() {
        List<String> list = List.of();
        Iterator<String> iterator = new BackwardIterator<>(list);

        List<String> actualList = new ArrayList<>();
        while (iterator.hasNext()) {
            assertAll(() -> actualList.add(iterator.next()));
        }
    }
}
