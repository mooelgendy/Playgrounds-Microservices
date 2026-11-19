package com.elgendy.playgroundservice.exception;

/**
 * Signals a request that is syntactically valid but semantically incomplete,
 * for example an update without an identifier.
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
