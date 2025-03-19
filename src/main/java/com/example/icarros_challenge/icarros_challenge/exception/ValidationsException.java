package com.example.icarros_challenge.icarros_challenge.exception;

import java.util.List;

/**
 * This class is an exception made up of a collection of 
 * validation exceptions
 */
public class ValidationsException extends RuntimeException {

    private final List<ValidationException> exceptions;

    public ValidationsException(List<ValidationException> exceptions) {
        this.exceptions = exceptions;
    }

    /**
     * Method to access the collection of exceptions 
     * @return a list of exceptions
     */
    public List<ValidationException> getExceptions() {
        return exceptions;
    }

}
