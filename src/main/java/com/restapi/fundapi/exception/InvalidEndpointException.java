package com.restapi.fundapi.exception;

public class InvalidEndpointException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidEndpointException(String message) {
        super(message);
    }

    public InvalidEndpointException(String message, Throwable cause) {
        super(message, cause);
    }
}
