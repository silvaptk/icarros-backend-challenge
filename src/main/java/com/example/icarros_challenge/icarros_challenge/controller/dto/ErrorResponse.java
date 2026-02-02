package com.example.icarros_challenge.icarros_challenge.controller.dto;

import com.example.icarros_challenge.icarros_challenge.controller.exception.DefaultException;

/**
 * Class that represents an error response to be sent to the client.
 * It contains a human-readable message as well as a code to be used
 * by any frontend clients
 * @param message
 * @param code
 */
public record ErrorResponse(String message, String code) {

    /**
     * Creates an instance of the class based on {@link DefaultException}
     * @param exception
     * @return
     */
    public static ErrorResponse fromDefaultException(DefaultException exception) {
        return new ErrorResponse(exception.getMessage(), exception.getCode());
    }

}
