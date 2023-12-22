package edu.hw10;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class Task2 {
    private Task2() {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface Cache {
        boolean persist() default false;

        /**
         * Provide a file for the caching of the methods information and return values
         *
         * @return string representation (path) of the file
         */
        String onDiskStorage() default "";
    }

    public record CacheProxy(Object object, Class<?> objectClass) implements InvocationHandler {
        private static final String INCORRECT_STORAGE_MSG = "Provided storage is not exists OR a directory, not a file";

        private static final String DELIMITER = "_:";
        private static final String KV_DELIMITER = "-:-";
        private static final Map<Method, Map<String, String>> RUNTIME_STORAGE = new HashMap<>();

        public static Object create(Object object, Class<?> objectClass) {
            return Proxy.newProxyInstance(
                objectClass.getClassLoader(),
                objectClass.getInterfaces(),
                new CacheProxy(object, objectClass)
            );
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                String maybeCachedValueString = tryGetFromCache(method, args);
                if (maybeCachedValueString != null) {
                    return parseFromStringToObject(maybeCachedValueString, method.getReturnType())[0];
                }
            } catch (InvalidPathException ignored) {
            } catch (IllegalArgumentException exception) {
                throw new RuntimeException(exception);
            } catch (Exception ignored) {
            }

            Object returnedValue = method.invoke(object, args);
            if (method.isAnnotationPresent(Cache.class)) {
                cache(method, returnedValue, args);
            }
            return returnedValue;
        }

        private void cache(Method method, Object value, Object[] args) {
            Cache cache = method.getAnnotation(Cache.class);
            boolean isPersist = cache.persist();

            if (isPersist) {
                Path storage = getPathToStorage(cache);
                if (storage.toFile().isDirectory()) {
                    throw new IllegalArgumentException(INCORRECT_STORAGE_MSG);
                }
                appendToFile(storage, value, args);
            } else {
                if (!RUNTIME_STORAGE.containsKey(method)) {
                    RUNTIME_STORAGE.put(method, new HashMap<>());
                }

                RUNTIME_STORAGE.get(method).put(
                    constructArgsString(args), constructValueString(value)
                );
            }
        }

        private static void appendToFile(Path file, Object value, Object[] args) {
            StringBuilder builder = new StringBuilder(readFromFile(file));
            builder.append(constructArgsString(args)).append(KV_DELIMITER)
                .append(constructValueString(value)).append("\n");

            try {
                Files.createFile(file.toAbsolutePath());
            } catch (IOException ignored) {
            }

            try {
                Files.writeString(file, builder.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static String readFromFile(Path file) {
            if (!file.toFile().exists()) {
                return "";
            }

            try {
                StringBuilder builder = new StringBuilder();
                Files.readAllLines(file).forEach(line -> builder.append(line).append("\n"));
                return builder.toString();
            } catch (IOException e) {
                throw new IllegalArgumentException("Provided file is incorrect");
            }
        }

        private static String tryGetFromCache(Method method, Object[] args) {
            String stringArgs = constructArgsString(args);

            Cache cache = method.getAnnotation(Cache.class);
            boolean isPersist = cache.persist();

            if (isPersist) {
                Path storage = getPathToStorage(cache);
                if (!storage.toFile().exists() || storage.toFile().isDirectory()) {
                    throw new InvalidPathException(storage.toString(), INCORRECT_STORAGE_MSG);
                }

                try {
                    for (String line : Files.readAllLines(storage)) {
                        String[] kv = line.split(KV_DELIMITER);
                        if (kv[0].equals(stringArgs)) {
                            return kv[1];
                        }
                    }
                    return null;
                } catch (IOException e) {
                    throw new RuntimeException("Something went wrong while reading from storage");
                }
            } else {
                if (!RUNTIME_STORAGE.containsKey(method)) {
                    return null;
                }
                var map = RUNTIME_STORAGE.get(method);
                return map.get(stringArgs);
            }
        }

        private static String constructArgsString(Object[] args) {
            if (args == null) {
                return "";
            }

            StringBuilder builder = new StringBuilder();
            for (Object object : args) {
                builder.append(object.getClass().cast(object)).append(DELIMITER);
            }
            return builder.substring(0, builder.length() - DELIMITER.length());
        }

        private static String constructValueString(Object value) {
            return value.getClass().cast(value).toString();
        }

        private static Object[] parseFromStringToObject(String value, Class<?>... objectTypes) {
            String[] string = value.split(DELIMITER);
            Object[] parsedObjects = new Object[string.length];
            for (int i = 0; i < string.length; i++) {
                var current = objectTypes[i];

                if (current.equals(Integer.class) || current.equals(int.class)) {
                    parsedObjects[i] = Integer.parseInt(string[i]);
                } else if (current.equals(Long.class) || current.equals(long.class)) {
                    parsedObjects[i] = Long.parseLong(string[i]);
                } else if (current.equals(String.class)) {
                    parsedObjects[i] = string[i];
                } else {
                    throw new IllegalArgumentException("This type is unsupported: " + current);
                }
            }
            return parsedObjects;
        }

        private static Path getPathToStorage(Cache cache) {
            return Path.of(cache.onDiskStorage());
        }
    }
}
