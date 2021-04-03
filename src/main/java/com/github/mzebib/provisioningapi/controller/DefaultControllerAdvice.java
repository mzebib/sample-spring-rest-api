package com.github.mzebib.provisioningapi.controller;

import com.github.mzebib.provisioningapi.exception.AccessDeniedException;
import com.github.mzebib.provisioningapi.exception.DatabaseException;
import com.github.mzebib.provisioningapi.exception.UnauthorizedException;
import com.github.mzebib.provisioningapi.exception.DuplicateEntityException;
import com.github.mzebib.provisioningapi.exception.NotFoundException;
import com.github.mzebib.provisioningapi.model.client.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author mzebib
 */
@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class })
    public ResponseEntity<ErrorResponse> processBadRequest(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ UnauthorizedException.class, SecurityException.class })
    public ResponseEntity<ErrorResponse> processUnauthorized(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ErrorResponse> processForbidden(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN, e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ErrorResponse> processNotFound(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ DuplicateEntityException.class })
    public ResponseEntity<ErrorResponse> processConflict(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT, e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ DatabaseException.class })
    public ResponseEntity<ErrorResponse> processInternalServerError(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
