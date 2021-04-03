package com.github.mzebib.provisioningapi.exception;

/**
 * @author mzebib
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}
