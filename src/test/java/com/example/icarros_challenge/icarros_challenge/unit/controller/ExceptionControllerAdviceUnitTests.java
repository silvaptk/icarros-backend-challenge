package com.example.icarros_challenge.icarros_challenge.unit.controller;

import com.example.icarros_challenge.icarros_challenge.controller.ExceptionControllerAdvice;
import com.example.icarros_challenge.icarros_challenge.controller.dto.ErrorResponse;
import com.example.icarros_challenge.icarros_challenge.controller.exception.MethodNotSupportedException;
import com.example.icarros_challenge.icarros_challenge.controller.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@Tag("unit")
public class ExceptionControllerAdviceUnitTests {

    private ExceptionControllerAdvice handler;

    @BeforeEach
    public void setup() {
        handler = new ExceptionControllerAdvice();
    }

    @Test
    public void testWithNoResourceFoundException() {
        NoResourceFoundException exception = new NoResourceFoundException(HttpMethod.POST, "");
        ResourceNotFoundException expectedException = new ResourceNotFoundException();

        ErrorResponse responseBody = handler.handleException(exception).getBody();

        Assertions.assertEquals(expectedException.getCode(), responseBody.code());
        Assertions.assertEquals(expectedException.getMessage(), responseBody.message());
    }

    @Test
    public void testWithHttpRequestMethodNotSupportedException() {
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("");
        MethodNotSupportedException expectedException = new MethodNotSupportedException();

        ErrorResponse responseBody = handler.handleException(exception).getBody();

        Assertions.assertEquals(expectedException.getCode(), responseBody.code());
        Assertions.assertEquals(expectedException.getMessage(), responseBody.message());
    }

    @Test
    public void testWithGenericException() {
        Exception exception = new RuntimeException("Unknown Error");

        ErrorResponse responseBody = handler.handleException(exception).getBody();

        Assertions.assertEquals("UNEXPECTED", responseBody.code());
    }

}
