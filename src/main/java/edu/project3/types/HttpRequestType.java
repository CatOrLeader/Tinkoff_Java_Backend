package edu.project3.types;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public enum HttpRequestType {
    GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE, PATCH;

    private static final HttpRequestType[] POSSIBLE_REQUESTS =
        new HttpRequestType[] {GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE, PATCH};

    public static Optional<HttpRequestType> parseRequestType(@NotNull String string) {
        for (HttpRequestType possibleType : POSSIBLE_REQUESTS) {
            if (possibleType.name().equals(string)) {
                return Optional.of(possibleType);
            }
        }

        return Optional.empty();
    }
}
