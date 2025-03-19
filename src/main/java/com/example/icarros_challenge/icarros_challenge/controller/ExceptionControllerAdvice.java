package com.example.icarros_challenge.icarros_challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.icarros_challenge.icarros_challenge.application.LogService;
import com.example.icarros_challenge.icarros_challenge.dto.ErrorResponse;
import com.example.icarros_challenge.icarros_challenge.exception.InvalidBodyException;
import com.example.icarros_challenge.icarros_challenge.exception.ValidationException;

/**
 * Class responsible for handling exceptions thrown in the entire 
 * application
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private final LogService logService;

    public ExceptionControllerAdvice(LogService logService) {
        this.logService = logService;
    }

    /**
     * This method will handle validation-related exceptions
     * @param exception A `ValidationException` instance 
     * @return An `ErrorResponse` describing the validation violation that happened
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception) {
        this.logService.handleMessage(exception.toString());

        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), exception.getCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBodyException(HttpMessageNotReadableException providedException) {
        InvalidBodyException exception = new InvalidBodyException();

        this.logService.handleMessage(exception.toString());

        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), exception.getCode()), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles all exceptions 
     * @param exception an `Exception` instance
     * @return An `ErrorResponse` describing the error that happened
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        this.logService.handleException(exception);

        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), "UNEXPECTED"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
