package edu.hw3;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Task3<T> {
    public Task3() {
    }

    @NotNull
    public Map<T, Integer> freqDict(@NotNull T[] values) {
        HashMap<T, Integer> dict = new HashMap<>();

        for (T str : values) {
            if (dict.containsKey(str)) {
                int oldValue = dict.get(str);
                dict.replace(str, oldValue, ++oldValue);
            } else {
                dict.put(str, 1);
            }
        }

        return dict;
    }
}
