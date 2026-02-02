package com.example.icarros_challenge.icarros_challenge.controller.exception;

/**
 * Encapsulates exceptions thrown by the application so every exception
 * handled has a code to be used by frontend applications to overwrite
 * the message to be displayed for the user
 */
public abstract class DefaultException extends RuntimeException {
    private final String code;

    public DefaultException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
