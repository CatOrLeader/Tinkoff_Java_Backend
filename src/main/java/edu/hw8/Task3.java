package edu.hw8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public final class Task3 {
    private Task3() {
    }

    public static final class MD5PasswordBreaker {
        private static final int HEXADECIMAL = 0xFF;
        private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final int ALPHABET_LENGTH = ALPHABET.length();
        private static final int LEFT_THRESHOLD = 4;
        private static final int RIGHT_THRESHOLD = 6 + 1;

        private final MessageDigest md;
        private final Map<String, String> database;

        public MD5PasswordBreaker(String leaks) throws NoSuchAlgorithmException {
            this.md = MessageDigest.getInstance("MD5");
            this.database =
                leaks.lines()
                    .collect(Collectors.toMap(
                            string -> string.split(" ")[0],
                            string -> string.split(" ")[1]
                        )
                    );
        }

        public Map<String, String> decryptSingleThread() {
            Map<String, String> decrypted = new HashMap<>();

            for (Map.Entry<String, String> entry : database.entrySet()) {
                this.md.reset();

                String key = entry.getKey();
                String value = entry.getValue();

                int[] maybePasswordIndexForm = new int[LEFT_THRESHOLD];
                String md5MaybePassword = encode(toString(maybePasswordIndexForm));
                while (!value.equals(md5MaybePassword)) {
                    try {
                        nextPassword(maybePasswordIndexForm);
                        md5MaybePassword = encode(toString(maybePasswordIndexForm));
                    } catch (ArrayIndexOutOfBoundsException resize) {
                        if (maybePasswordIndexForm.length == RIGHT_THRESHOLD) {
                            throw new RuntimeException("Incorrect password length (not in range 4 - 6)");
                        }
                        maybePasswordIndexForm = new int[maybePasswordIndexForm.length + 1];
                    }
                }

                decrypted.put(key, toString(maybePasswordIndexForm));
            }

            return decrypted;
        }

        public Map<String, String> decryptMultiThread(int countOfThreads)
            throws ExecutionException, InterruptedException {
            if (countOfThreads < 1) {
                throw new IllegalArgumentException();
            }

            Map<String, String> decrypted = Collections.synchronizedMap(new HashMap<>());
            for (Map.Entry<String, String> entry : database.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                try (
                    ExecutorService executor = Executors.newFixedThreadPool(countOfThreads)
                ) {
                    int currentLength = LEFT_THRESHOLD;
                    for (int i = 0; i < countOfThreads; i++) {
                        if (i != 0 && i % 2 == 0) {
                            currentLength++;
                        }

                        Future<String> maybePassword = executor.submit(new MD5PasswordBreakerWorker(
                            currentLength,
                            i % 2 == 0,
                            value,
                            this
                        ));

                        if (maybePassword.get() != null) {
                            executor.shutdownNow();
                            decrypted.put(key, maybePassword.get());
                            break;
                        }
                    }
                }
            }

            return decrypted;
        }

        private void nextPassword(int[] previousPasswordIndexForm) {
            int i = previousPasswordIndexForm.length - 1;

            while (i >= 0) {
                previousPasswordIndexForm[i]++;

                if (previousPasswordIndexForm[i] == ALPHABET_LENGTH) {
                    previousPasswordIndexForm[i] = 0;
                    i--;
                } else {
                    return;
                }
            }

            throw new ArrayIndexOutOfBoundsException();
        }

        private String toString(int[] password) {
            StringBuilder builder = new StringBuilder();
            for (int index : password) {
                builder.append(ALPHABET.charAt(index));
            }
            return builder.toString();
        }

        private String encode(String message) {
            this.md.reset();
            return toHexString(this.md.digest(message.getBytes()));
        }

        private String toHexString(byte[] bytes) {
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                String val = Integer.toHexString(HEXADECIMAL & b);
                if (val.length() == 1) {
                    builder.append('0');
                }
                builder.append(val);
            }
            return builder.toString();
        }

        private record MD5PasswordBreakerWorker(int length, boolean leftAlphabet, String md5Password,
                                                MD5PasswordBreaker breaker) implements Callable<String> {

            @Override
            public String call() {
                int[] maybePasswordIndexForm = new int[length];
                if (!leftAlphabet) {
                    maybePasswordIndexForm[0] = ALPHABET_LENGTH / 2;
                }

                String md5MaybePassword;
                while (true) {
                    try {
                        breaker.nextPassword(maybePasswordIndexForm);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        return null;
                    }

                    md5MaybePassword = breaker.encode(breaker.toString(maybePasswordIndexForm));

                    if (md5Password.equals(md5MaybePassword)) {
                        return breaker.toString(maybePasswordIndexForm);
                    }

                    if (leftAlphabet && maybePasswordIndexForm[0] == ALPHABET_LENGTH / 2) {
                        return null;
                    }
                }
            }
        }
    }
}
