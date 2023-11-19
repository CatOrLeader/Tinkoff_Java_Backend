package edu.project3;

import edu.project3.types.HttpRequestType;
import java.time.OffsetDateTime;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class LogRecord {
    private final String address;
    private final String user;
    private final OffsetDateTime time;
    private final HttpRequestType request;
    private final String resource;
    private final String protocol;
    private final int status;
    private final int bodyBytesSent;
    private final String httpReferer;
    private final String httpUserAgent;

    public LogRecord(@NotNull Builder builder) {
        this.address = builder.address;
        this.user = builder.user;
        this.time = builder.time;
        this.request = builder.request;
        this.resource = builder.resource;
        this.protocol = builder.protocol;
        this.status = builder.status;
        this.bodyBytesSent = builder.bodyBytesSent;
        this.httpReferer = builder.httpReferer;
        this.httpUserAgent = builder.httpUserAgent;
    }

    public String getAddress() {
        return address;
    }

    public String getUser() {
        return user;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public HttpRequestType getRequest() {
        return request;
    }

    public String getResource() {
        return resource;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getStatus() {
        return status;
    }

    public int getBodyBytesSent() {
        return bodyBytesSent;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogRecord logRecord = (LogRecord) o;
        return status == logRecord.status
               && bodyBytesSent == logRecord.bodyBytesSent
               && Objects.equals(address, logRecord.address) && Objects.equals(user, logRecord.user)
               && Objects.equals(time, logRecord.time) && request == logRecord.request
               && Objects.equals(resource, logRecord.resource) && Objects.equals(protocol, logRecord.protocol)
               && Objects.equals(httpReferer, logRecord.httpReferer)
               && Objects.equals(httpUserAgent, logRecord.httpUserAgent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            address,
            user,
            time,
            request,
            resource,
            protocol,
            status,
            bodyBytesSent,
            httpReferer,
            httpUserAgent
        );
    }

    public static final class Builder {
        private String address = null;
        private String user = null;
        private OffsetDateTime time = null;
        private HttpRequestType request = null;
        private String resource = null;
        private String protocol = null;
        private Integer status = null;
        private Integer bodyBytesSent = null;
        private String httpReferer = null;
        private String httpUserAgent = null;

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder setAddress(@NotNull String address) {
            this.address = address;
            return this;
        }

        public Builder setUser(@NotNull String user) {
            this.user = user;
            return this;
        }

        public Builder setTime(@NotNull OffsetDateTime time) {
            this.time = time;
            return this;
        }

        public Builder setRequest(@NotNull HttpRequestType request) {
            this.request = request;
            return this;
        }

        public Builder setResource(@NotNull String resource) {
            this.resource = resource;
            return this;
        }

        public Builder setProtocol(@NotNull String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder setStatus(@NotNull Integer status) {
            this.status = status;
            return this;
        }

        public Builder setBodyBytesSent(@NotNull Integer bodyBytesSent) {
            this.bodyBytesSent = bodyBytesSent;
            return this;
        }

        public Builder setHttpReferer(@NotNull String httpReferer) {
            this.httpReferer = httpReferer;
            return this;
        }

        public Builder setHttpUserAgent(@NotNull String httpUserAgent) {
            this.httpUserAgent = httpUserAgent;
            return this;
        }

        public LogRecord build() {
            if (!isSuccessfullyAssembled()) {
                throw new ExceptionInInitializerError("Log is not assembled correctly, some fields are missing");
            }

            return new LogRecord(this);
        }

        private boolean isSuccessfullyAssembled() {
            return (this.address != null) && (this.user != null) && (this.time != null) && (this.request != null)
                   && (this.resource != null) && (this.protocol != null) && (this.status != null)
                   && (this.bodyBytesSent != null) && (this.httpReferer != null)
                   && (this.httpUserAgent != null);
        }
    }
}
