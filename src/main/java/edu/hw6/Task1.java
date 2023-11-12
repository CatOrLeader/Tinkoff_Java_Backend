package edu.hw6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Task1 {
    private Task1() {
    }

    /**
     * <p>
     * Class with the implementation of the DiskMap.
     * This DS supports the connection to the files to get data from them.
     *
     * @implSpec HashMap
     * @see java.util.HashMap HashMap
     *     </p>
     */
    public static class DiskMap implements Map<String, String> {
        private final Map<String, String> map;

        public DiskMap() {
            map = new HashMap<>();
        }

        /**
         * Get key-value pairs from the file and add them to the current map. Only append new data and overwrite a
         * decent one if needed. Do not touch anything in the file.
         *
         * @param source file with new data
         */
        public void readFromFile(File source) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(source));
            for (String line : reader.lines().toList()) {
                if (line.isEmpty() || line.isBlank()) {
                    continue;
                }
                String[] keyValue = line.trim().split(":");
                map.put(keyValue[0], keyValue[1]);
            }
            reader.close();
        }

        /**
         * Write the map onto the file in the next format: key:value\n. If the file does not exist, create one.
         * Overwrite all the information that has already been placed in the file.
         *
         * @param destination file; will be created if not exist already
         */
        public void writeToFile(File destination) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
            for (var entry : map.entrySet()) {
                String toFile = entry.getKey() + ":" + entry.getValue();
                writer.write(toFile.trim());
                writer.write("\n");
            }
            writer.close();
        }

        // Default methods
        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return map.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return map.containsValue(value);
        }

        @Override
        public String get(Object key) {
            return map.get(key);
        }

        @Nullable
        @Override
        public String put(String key, String value) {
            return map.put(key, value);
        }

        @Override
        public String remove(Object key) {
            return map.remove(key);
        }

        @Override
        public void putAll(@NotNull Map<? extends String, ? extends String> m) {
            map.putAll(m);
        }

        @Override
        public void clear() {
            map.clear();
        }

        @NotNull
        @Override
        public Set<String> keySet() {
            return map.keySet();
        }

        @NotNull
        @Override
        public Collection<String> values() {
            return map.values();
        }

        @NotNull
        @Override
        public Set<Entry<String, String>> entrySet() {
            return map.entrySet();
        }
    }
}
