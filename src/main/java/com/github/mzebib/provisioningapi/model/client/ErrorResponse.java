package com.github.mzebib.provisioningapi.model.client;

import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * @author mzebib
 */
public class ErrorResponse implements Response {

    private final String timestamp = Instant.now().toString();
    private final int statusCode;
    private final String statusReason;
    private final String message;

    public ErrorResponse(HttpStatus status, String message) {
        if (status == null) throw new NullPointerException("HttpStatus cannot be null");

        this.statusCode = status.value();
        this.statusReason = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorResponse(int statusCode, String statusReason, String message) {
        this.statusCode = statusCode;
        this.statusReason = statusReason;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public String getMessage() {
        return message;
    }
}
