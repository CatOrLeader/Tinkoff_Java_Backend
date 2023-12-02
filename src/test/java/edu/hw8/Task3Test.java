package edu.hw8;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Single thread")
    void singleThread() throws NoSuchAlgorithmException {
        String[] encryptedPasswords = new String[] {
            "214bc0aec070fe2c6005099ce7a0ab0e",
            "4100c4d44da9177247e44a5fc1546778"
        };
        String leaks = "artur " + encryptedPasswords[0] + "\nnumber " + encryptedPasswords[1];
        Task3.MD5PasswordBreaker breaker = new Task3.MD5PasswordBreaker(leaks);

        Map<String, String> actualValues = breaker.decryptSingleThread();
        Map<String, String> expectedValues = Map.of(
            "artur", "jici",
            "number", "01234"
        );

        assertThat(actualValues.entrySet()).containsExactlyInAnyOrderElementsOf(expectedValues.entrySet());
    }

    @Test
    @DisplayName("Multi thread")
    void multiThread() throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
        String[] encryptedPasswords = new String[] {
            "214bc0aec070fe2c6005099ce7a0ab0e",
            "e8031b03195b431f972c91b894db24ca"
        };
        String leaks = "artur " + encryptedPasswords[0] + "\nnumber " + encryptedPasswords[1];
        Task3.MD5PasswordBreaker breaker = new Task3.MD5PasswordBreaker(leaks);

        Map<String, String> actualValues = breaker.decryptMultiThread(4);
        Map<String, String> expectedValues = Map.of(
            "artur", "jici",
            "number", "wZZZ"
        );

        assertThat(actualValues.entrySet()).containsExactlyInAnyOrderElementsOf(expectedValues.entrySet());
    }
}
