package edu.hw3;

import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.fail;

public class Task3Test {
    @Test
    @DisplayName("Two for two words")
    void twoForTwoWords() {
        String[] values = new String[] {"a", "bb", "a", "bb"};

        Map<String, Integer> actualDict = new Task3<String>().freqDict(values);
        Map<String, Integer> expectedDict = Map.of(
            "a", 2,
            "bb", 2
        );

        if (actualDict.size() != expectedDict.size()) {
            fail("Different size of the maps");
        }
        for (Map.Entry<String, Integer> entry : actualDict.entrySet()) {
            if (expectedDict.get(entry.getKey()) == null
                || !Objects.equals(expectedDict.get(entry.getKey()), entry.getValue())) {
                fail("incorrect values mapped");
            }
        }
    }

    @Test
    @DisplayName("Three different words")
    void threeDifferentWords() {
        String[] values = new String[] {"this", "and", "that", "and"};

        Map<String, Integer> actualDict = new Task3<String>().freqDict(values);
        Map<String, Integer> expectedDict = Map.of(
            "that", 1,
            "and", 2,
            "this", 1
        );

        if (actualDict.size() != expectedDict.size()) {
            fail("Different size of the maps");
        }
        for (Map.Entry<String, Integer> entry : actualDict.entrySet()) {
            if (expectedDict.get(entry.getKey()) == null
                || !Objects.equals(expectedDict.get(entry.getKey()), entry.getValue())) {
                fail("incorrect values mapped");
            }
        }
    }

    @Test
    @DisplayName("Mixed words")
    void mixedWords() {
        String[] values = new String[] {"код", "код", "код", "bug"};

        Map<String, Integer> actualDict = new Task3<String>().freqDict(values);
        Map<String, Integer> expectedDict = Map.of(
            "код", 3,
            "bug", 1
        );

        if (actualDict.size() != expectedDict.size()) {
            fail("Different size of the maps");
        }
        for (Map.Entry<String, Integer> entry : actualDict.entrySet()) {
            if (expectedDict.get(entry.getKey()) == null
                || !Objects.equals(expectedDict.get(entry.getKey()), entry.getValue())) {
                fail("incorrect values mapped");
            }
        }
    }

    @Test
    @DisplayName("Integer keys")
    void integerKeys() {
        Integer[] values = new Integer[] {1, 1, 2, 2};

        Map<Integer, Integer> actualDict = new Task3<Integer>().freqDict(values);
        Map<Integer, Integer> expectedDict = Map.of(
            1, 2,
            2, 2
        );

        if (actualDict.size() != expectedDict.size()) {
            fail("Different size of the maps");
        }
        for (Map.Entry<Integer, Integer> entry : actualDict.entrySet()) {
            if (expectedDict.get(entry.getKey()) == null
                || !Objects.equals(expectedDict.get(entry.getKey()), entry.getValue())) {
                fail("incorrect values mapped");
            }
        }
    }

    @Test
    @DisplayName("Zero sized array")
    void zeroSizedArray() {
        Integer[] values = new Integer[0];

        Map<Integer, Integer> actualDict = new Task3<Integer>().freqDict(values);
        Map<Integer, Integer> expectedDict = Map.of(
        );

        if (actualDict.size() != expectedDict.size()) {
            fail("Different size of the maps");
        }
        for (Map.Entry<Integer, Integer> entry : actualDict.entrySet()) {
            if (expectedDict.get(entry.getKey()) == null
                || !Objects.equals(expectedDict.get(entry.getKey()), entry.getValue())) {
                fail("incorrect values mapped");
            }
        }
    }
}
