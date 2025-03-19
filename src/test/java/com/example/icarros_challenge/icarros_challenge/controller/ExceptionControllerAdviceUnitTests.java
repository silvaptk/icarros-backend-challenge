package com.example.icarros_challenge.icarros_challenge.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import com.example.icarros_challenge.icarros_challenge.application.LogService;
import com.example.icarros_challenge.icarros_challenge.dto.ErrorResponse;
import com.example.icarros_challenge.icarros_challenge.exception.InvalidBodyException;
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
    public void testWithUnsupportedMediaException() {
        HttpMediaTypeNotSupportedException providedException = new HttpMediaTypeNotSupportedException("DUMMY_MESSAGE");
        InvalidBodyException expectedException = new InvalidBodyException();

        ErrorResponse responseBody = handler.handleInvalidBodyException(providedException).getBody();

        Assertions.assertEquals(expectedException.getMessage(), responseBody.message());
        Assertions.assertEquals(expectedException.getCode(), responseBody.code());
    }

    @Test
    public void testWithUnreadableMessageException() {
        HttpMessageNotReadableException providedException = new HttpMessageNotReadableException("DUMMY_MESSAGE", new MockHttpInputMessage("".getBytes()));
        InvalidBodyException expectedException = new InvalidBodyException();

        ErrorResponse responseBody = handler.handleInvalidBodyException(providedException).getBody();

        Assertions.assertEquals(expectedException.getMessage(), responseBody.message());
        Assertions.assertEquals(expectedException.getCode(), responseBody.code());
    }

    @Test
    public void testWithValidationException() {
        PasswordTooShortException exception = new PasswordTooShortException(9);

        ErrorResponse responseBody = handler.handleValidationException(exception).getBody();

        Assertions.assertEquals(exception.getMessage(), responseBody.message());
        Assertions.assertEquals(exception.getCode(), responseBody.code());
    }
}
