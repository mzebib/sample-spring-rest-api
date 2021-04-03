package com.github.mzebib.provisioningapi.exception;

/**
 * @author mzebib
 */
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException() {
        super();
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}
