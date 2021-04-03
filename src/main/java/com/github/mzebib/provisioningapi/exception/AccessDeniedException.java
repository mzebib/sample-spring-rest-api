package com.github.mzebib.provisioningapi.exception;

/**
 * @author mzebib
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super();
    }

    public AccessDeniedException(String message) {
        super(message);
    }

}
