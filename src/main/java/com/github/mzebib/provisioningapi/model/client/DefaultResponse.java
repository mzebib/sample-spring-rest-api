package com.github.mzebib.provisioningapi.model.client;

import java.time.Instant;

/**
 * @author mzebib
 */
public class DefaultResponse implements Response {

    private String timestamp = Instant.now().toString();
    private String message;

    public DefaultResponse() {
    }

    public DefaultResponse(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
