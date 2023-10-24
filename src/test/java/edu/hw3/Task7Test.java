package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import static edu.hw3.Task7.NullIncludedComparator;
import static org.assertj.core.api.Assertions.assertThat;

public class Task7Test {
    @Test
    @DisplayName("<String, String> tree map")
    void stringStringTreeMap() {
        TreeMap<String, String> treeMap;

        treeMap = new TreeMap<>(new NullIncludedComparator<>());
        treeMap.put(null, "test");

        assertThat(treeMap.containsKey(null)).isTrue();
    }

    @Test
    @DisplayName("<String, String> tree map, two additions")
    void stringStringTreeMap_twoAdditions() {
        TreeMap<String, String> treeMap;
        treeMap = new TreeMap<>(new NullIncludedComparator<>());
        treeMap.put(null, "test");
        treeMap.put("correct value", "lol");

        String[] actualSet = treeMap.keySet().toArray(new String[0]);
        String[] expectedSet = new String[] {null, "correct value"};

        assertThat(actualSet).containsExactly(expectedSet);
    }

    @Test
    @DisplayName("<String, String> tree map, three additions")
    void stringStringTreeMap_threeAdditions() {
        TreeMap<String, String> treeMap;
        treeMap = new TreeMap<>(new NullIncludedComparator<>());
        treeMap.put(null, "test");
        treeMap.put(null, "lol");
        treeMap.put("correct value", "lol");

        String[] actualSet = treeMap.keySet().toArray(new String[0]);
        String[] expectedSet = new String[] {null, "correct value"};

        assertThat(actualSet).containsExactly(expectedSet);
    }

    @Test
    @DisplayName("<Integer, Double> tree map, two additions, check values")
    void integerDoubleTreeMap_twoAdditions_checkValues() {
        TreeMap<Integer, Double> treeMap;
        treeMap = new TreeMap<>(new NullIncludedComparator<>());
        treeMap.put(1, 2.0);
        treeMap.put(null, 1.0);

        Integer[] actualKeys = treeMap.keySet().toArray(new Integer[0]);
        Integer[] expectedKeys = new Integer[] {null, 1};

        Double[] actualValues = treeMap.values().toArray(new Double[0]);
        Double[] expectedValues = new Double[] {1.0, 2.0};

        assertThat(actualKeys).containsExactly(expectedKeys);
        assertThat(actualValues).containsExactly(expectedValues);
    }
}
