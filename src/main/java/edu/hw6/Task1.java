package edu.hw6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
        private static final String MAPPED_FILE_EXCEPTION = "Exception raised while trying to read from mapped file";

        private File currentMappedFile;
        private final Map<String, String> map;

        public DiskMap(@NotNull File source) {
            currentMappedFile = source;
            map = new HashMap<>();
            readFromCurrentMappedFile();
        }

        public void switchCurrentMappedFile(@NotNull File newSource, boolean remainDataInMap) throws IOException {
            if (!newSource.exists()) {
                throw new IOException("New source file does not exists");
            }

            writeToCurrentMappedFile();
            if (!remainDataInMap) {
                map.clear();
            }
            currentMappedFile = newSource;
            readFromCurrentMappedFile();
        }

        public @NotNull File getCurrentMappedFile() {
            return currentMappedFile;
        }

        /**
         * Get key-value pairs from the file and add them to the current map. Only append new data and overwrite a
         * decent one if needed. Do not touch anything in the file.
         *
         * @param source file with new data
         */
        public void readFromFile(File source) throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
                for (String line : reader.lines().toList()) {
                    if (line.isEmpty() || line.isBlank()) {
                        continue;
                    }
                    String[] keyValue = line.trim().split(":");
                    map.put(keyValue[0], keyValue[1]);
                }
            } catch (FileNotFoundException ex) {
                throw new IOException("No file with such name exists: " + source);
            }
        }

        private void readFromCurrentMappedFile() {
            try {
                readFromFile(currentMappedFile);
            } catch (IOException exception) {
                throw new RuntimeException(MAPPED_FILE_EXCEPTION);
            }
        }

        /**
         * Write the map onto the file in the next format: key:value\n. If the file does not exist, create one.
         * Overwrite all the information that has already been placed in the file.
         *
         * @param destination file; will be created if not exist already
         */
        public void writeToFile(File destination) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(destination))) {
                for (var entry : map.entrySet()) {
                    String toFile = entry.getKey() + ":" + entry.getValue();
                    writer.write(toFile.trim());
                    writer.write("\n");
                }
            } catch (IOException ex) {
                throw new IOException("Exception raised while writing to the file " + destination);
            }
        }

        private void writeToCurrentMappedFile() {
            try {
                writeToFile(currentMappedFile);
            } catch (IOException exception) {
                throw new RuntimeException(MAPPED_FILE_EXCEPTION);
            }
        }

        // Default methods
        @Override
        public int size() {
            readFromCurrentMappedFile();
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            readFromCurrentMappedFile();
            return map.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            readFromCurrentMappedFile();
            return map.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            readFromCurrentMappedFile();
            return map.containsValue(value);
        }

        @Override
        public String get(Object key) {
            readFromCurrentMappedFile();
            return map.get(key);
        }

        @Nullable
        @Override
        public String put(String key, String value) {
            readFromCurrentMappedFile();
            String ret = map.put(key, value);
            writeToCurrentMappedFile();
            return ret;
        }

        @Override
        public String remove(Object key) {
            readFromCurrentMappedFile();
            String ret = map.remove(key);
            writeToCurrentMappedFile();
            return ret;
        }

        @Override
        public void putAll(@NotNull Map<? extends String, ? extends String> m) {
            readFromCurrentMappedFile();
            map.putAll(m);
            writeToCurrentMappedFile();
        }

        @Override
        public void clear() {
            map.clear();
            writeToCurrentMappedFile();
        }

        @NotNull
        @Override
        public Set<String> keySet() {
            readFromCurrentMappedFile();
            return map.keySet();
        }

        @NotNull
        @Override
        public Collection<String> values() {
            readFromCurrentMappedFile();
            return map.values();
        }

        @NotNull
        @Override
        public Set<Entry<String, String>> entrySet() {
            readFromCurrentMappedFile();
            return map.entrySet();
        }
    }
}
