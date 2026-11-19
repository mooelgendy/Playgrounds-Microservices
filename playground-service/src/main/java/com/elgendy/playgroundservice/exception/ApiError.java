package com.elgendy.playgroundservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * Stable error payload returned by every failing endpoint.
 *
 * <p>The previous payload exposed the exception instance itself, including its
 * stack trace, to API clients. This version only carries data that is safe to
 * share.</p>
 */
@Getter
public class ApiError {

    private final String timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final List<FieldError> fieldErrors;

    public ApiError(HttpStatus status, String message, String path, List<FieldError> fieldErrors) {
        this.timestamp = Instant.now().toString();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
        this.fieldErrors = fieldErrors == null ? Collections.emptyList() : fieldErrors;
    }

    @Getter
    public static class FieldError {

        private final String field;
        private final String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
