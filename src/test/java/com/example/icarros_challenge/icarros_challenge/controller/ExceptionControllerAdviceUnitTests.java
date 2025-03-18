package com.example.icarros_challenge.icarros_challenge.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import com.example.icarros_challenge.icarros_challenge.application.LogService;
import com.example.icarros_challenge.icarros_challenge.dto.ErrorResponse;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordTooShortException;

@Tag("unit")
public class ExceptionControllerAdviceUnitTests {
    private ExceptionControllerAdvice handler;
    private LogService logServiceMock;

    @BeforeEach
    public void setup() {
        logServiceMock = mock(LogService.class);
        handler = new ExceptionControllerAdvice(logServiceMock);
    }

    @Test
    public void testWithGenericException() {
        Exception exception = new RuntimeException("DUMMY_MESSAGE");

        ErrorResponse responseBody = handler.handleException(exception).getBody();

        Assertions.assertEquals(exception.getMessage(), responseBody.message());
    }

    @Test
    public void testWithValidationException() {
        PasswordTooShortException exception = new PasswordTooShortException(9);

        ErrorResponse responseBody = handler.handleValidationException(exception).getBody();

        Assertions.assertEquals(exception.getMessage(), responseBody.message());
        Assertions.assertEquals(exception.getCode(), responseBody.code());
    }
}
