package edu.project3;

import edu.project3.types.HttpRequestType;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogRecordTest {
    private static final String THROWABLE_MSG = "Log is not assembled correctly, some fields are missing";

    @Test
    @DisplayName("Address is not provided")
    void addressFieldIsMissing() {
        LogRecord.Builder builder = LogRecord.Builder.newInstance()
            .setUser("User")
            .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
            .setRequest(HttpRequestType.GET)
            .setResource("Resource")
            .setProtocol("Protocol")
            .setStatus(200)
            .setBodyBytesSent(0)
            .setHttpReferer("Referer")
            .setHttpUserAgent("Agent");

        assertThatExceptionOfType(ExceptionInInitializerError.class)
            .isThrownBy(builder::build)
            .withMessage(THROWABLE_MSG);
    }

    @Test
    @DisplayName("User is not provided")
    void userFieldIsMissing() {
        LogRecord.Builder builder = LogRecord.Builder.newInstance()
            .setAddress("Address")
            .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
            .setRequest(HttpRequestType.GET)
            .setResource("Resource")
            .setProtocol("Protocol")
            .setStatus(200)
            .setBodyBytesSent(0)
            .setHttpReferer("Referer")
            .setHttpUserAgent("Agent");

        assertThatExceptionOfType(ExceptionInInitializerError.class)
            .isThrownBy(builder::build)
            .withMessage(THROWABLE_MSG);
    }

    @Test
    @DisplayName("Time is not provided")
    void timeFieldIsMissing() {
        LogRecord.Builder builder = LogRecord.Builder.newInstance()
            .setAddress("Address")
            .setUser("User")
            .setRequest(HttpRequestType.GET)
            .setResource("Resource")
            .setProtocol("Protocol")
            .setStatus(200)
            .setBodyBytesSent(0)
            .setHttpReferer("Referer")
            .setHttpUserAgent("Agent");

        assertThatExceptionOfType(ExceptionInInitializerError.class)
            .isThrownBy(builder::build)
            .withMessage(THROWABLE_MSG);
    }

    @Test
    @DisplayName("Request is not provided")
    void requestFieldIsMissing() {
        LogRecord.Builder builder = LogRecord.Builder.newInstance()
            .setAddress("Address")
            .setUser("User")
            .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
            .setResource("Resource")
            .setProtocol("Protocol")
            .setStatus(200)
            .setBodyBytesSent(0)
            .setHttpReferer("Referer")
            .setHttpUserAgent("Agent");

        assertThatExceptionOfType(ExceptionInInitializerError.class)
            .isThrownBy(builder::build)
            .withMessage(THROWABLE_MSG);
    }

    @Test
    @DisplayName("Resource is not provided")
    void resourceFieldIsMissing() {
        LogRecord.Builder builder = LogRecord.Builder.newInstance()
            .setAddress("Address")
            .setUser("User")
            .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
            .setRequest(HttpRequestType.GET)
            .setProtocol("Protocol")
            .setStatus(200)
            .setBodyBytesSent(0)
            .setHttpReferer("Referer")
            .setHttpUserAgent("Agent");

        assertThatExceptionOfType(ExceptionInInitializerError.class)
            .isThrownBy(builder::build)
            .withMessage(THROWABLE_MSG);
    }

    @Test
    @DisplayName("Protocol is not provided")
    void protocolFieldIsMissing() {
        LogRecord.Builder builder = LogRecord.Builder.newInstance()
            .setAddress("Address")
            .setUser("User")
            .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
            .setRequest(HttpRequestType.GET)
            .setResource("Resource")
            .setStatus(200)
            .setBodyBytesSent(0)
            .setHttpReferer("Referer")
            .setHttpUserAgent("Agent");

        assertThatExceptionOfType(ExceptionInInitializerError.class)
            .isThrownBy(builder::build)
            .withMessage(THROWABLE_MSG);
    }

    @Test
    @DisplayName("Referrer is not provided")
    void referrerFieldIsMissing() {
        LogRecord.Builder builder = LogRecord.Builder.newInstance()
            .setAddress("Address")
            .setUser("User")
            .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
            .setRequest(HttpRequestType.GET)
            .setResource("Resource")
            .setProtocol("Protocol")
            .setStatus(200)
            .setBodyBytesSent(0)
            .setHttpUserAgent("Agent");

        assertThatExceptionOfType(ExceptionInInitializerError.class)
            .isThrownBy(builder::build)
            .withMessage(THROWABLE_MSG);
    }

    @Test
    @DisplayName("Agent is not provided")
    void agentFieldIsMissing() {
        LogRecord.Builder builder = LogRecord.Builder.newInstance()
            .setAddress("Address")
            .setUser("User")
            .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
            .setRequest(HttpRequestType.GET)
            .setResource("Resource")
            .setProtocol("Protocol")
            .setStatus(200)
            .setBodyBytesSent(0)
            .setHttpReferer("Referer");

        assertThatExceptionOfType(ExceptionInInitializerError.class)
            .isThrownBy(builder::build)
            .withMessage(THROWABLE_MSG);
    }

    @Test
    @DisplayName("Correct equality")
    void correctEquality() {
        LogRecord.Builder builder = LogRecord.Builder.newInstance()
            .setUser("User")
            .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
            .setRequest(HttpRequestType.GET)
            .setResource("Resource")
            .setProtocol("Protocol")
            .setStatus(200)
            .setAddress("a")
            .setBodyBytesSent(0)
            .setHttpReferer("Referer")
            .setHttpUserAgent("Agent");

        LogRecord.Builder builderSecond = LogRecord.Builder.newInstance()
            .setUser("User")
            .setTime(OffsetDateTime.of(2023, 11, 2, 22, 11, 0, 0, ZoneOffset.UTC))
            .setRequest(HttpRequestType.GET)
            .setResource("Resource")
            .setProtocol("Protocol")
            .setStatus(200)
            .setAddress("a")
            .setBodyBytesSent(0)
            .setHttpReferer("Referer")
            .setHttpUserAgent("Agent");

        assertThat(builder.build()).isEqualTo(builderSecond.build());
    }
}
