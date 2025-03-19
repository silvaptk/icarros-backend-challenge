package com.example.icarros_challenge.icarros_challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.icarros_challenge.icarros_challenge.application.LogService;
import com.example.icarros_challenge.icarros_challenge.dto.ErrorResponse;
import com.example.icarros_challenge.icarros_challenge.dto.ErrorsResponse;
import com.example.icarros_challenge.icarros_challenge.exception.InvalidBodyException;
import com.example.icarros_challenge.icarros_challenge.exception.ValidationException;
import com.example.icarros_challenge.icarros_challenge.exception.ValidationsException;

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
    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<ErrorsResponse> handleValidationsException(ValidationsException exception) {
        String exceptionsMessage = "";

        List<ValidationException> exceptions = exception.getExceptions();
        ArrayList<ErrorResponse> responses = new ArrayList<>();

        for (ValidationException validationException : exceptions) {
            exceptionsMessage += validationException.toString() + "\n";
            responses.add(new ErrorResponse(validationException.getMessage(), validationException.getCode()));
        }

        this.logService.handleMessage(exceptionsMessage);

        return new ResponseEntity<>(new ErrorsResponse(responses), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles unreadable bodies
     * @param providedException
     * @return An `ErrorResponse` telling that the body is invalid
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBodyException(HttpMessageNotReadableException providedException) {
        InvalidBodyException exception = new InvalidBodyException();

        this.logService.handleMessage(exception.toString());

        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), exception.getCode()), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles unsupported bodies
     * @param providedException
     * @return An `ErrorResponse` telling that the body is invalid
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBodyException(HttpMediaTypeNotSupportedException providedException) {
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
