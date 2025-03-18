package com.example.icarros_challenge.icarros_challenge.exception;

/**
 * This class represents a validation exception that may be thrown
 * if the client input is invalid
 */
public abstract class ValidationException extends RuntimeException {
    private final String code;

    public ValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * This method returns a code to be used by machines
     * @return string the code
     */
    public String getCode() {
        return this.code;
    }
    
}
