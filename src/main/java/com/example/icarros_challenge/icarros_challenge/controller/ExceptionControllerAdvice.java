package com.example.icarros_challenge.icarros_challenge.controller;

import com.example.icarros_challenge.icarros_challenge.controller.dto.ErrorResponse;
import com.example.icarros_challenge.icarros_challenge.controller.exception.MethodNotSupportedException;
import com.example.icarros_challenge.icarros_challenge.controller.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;


/**
 * Class responsible for handling exceptions thrown in the entire 
 * application
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    public ExceptionControllerAdvice() {}

    /**
     * This method handles exceptions thrown due to requests for resources
     * that were not implemented
     * @param exception an {@link ResourceNotFoundException} instance
     * @return An {@link ErrorResponse} describing the error that happened
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(NoResourceFoundException exception) {
        return new ResponseEntity<>(
                ErrorResponse.fromDefaultException(new ResourceNotFoundException()),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * This method handles exceptions thrown due to requests for existing
     * resources with incorrect HTTP methods
     * @param exception an {@link HttpRequestMethodNotSupportedException} instance
     * @return An {@link ErrorResponse} describing the error that happened
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpRequestMethodNotSupportedException exception) {
        return new ResponseEntity<>(
                ErrorResponse.fromDefaultException(new MethodNotSupportedException()),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    /**
     * This method handles all exceptions
     * @param exception an {@link Exception} instance
     * @return An {@link ErrorResponse} describing the error that happened
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return new ResponseEntity<>(
                new ErrorResponse("Erro inesperado. Estamos trabalhando para resolver.", "UNEXPECTED"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
