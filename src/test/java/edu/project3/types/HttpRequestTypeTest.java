package edu.project3.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTypeTest {
    @Test
    @DisplayName("Correct request type")
    void correctRequest() {
        String maybeRequest = "GET";

        HttpRequestType actualValue = HttpRequestType.parseRequestType(maybeRequest).orElseThrow();
        HttpRequestType expectedValue = HttpRequestType.GET;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect request type")
    void incorrectRequest() {
        String maybeRequest = "GOT";

        assertThat(HttpRequestType.parseRequestType(maybeRequest)).isEmpty();
    }
}
