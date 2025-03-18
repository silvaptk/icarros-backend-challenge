package com.example.icarros_challenge.icarros_challenge.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.icarros_challenge.icarros_challenge.wrappers.SentryWrapper;

@Tag("unit")
public class LogServiceUnitTests {
    private LogService service;
    private SentryWrapper wrapperMock; 

    @BeforeEach
    public void setup() {
        wrapperMock = mock(SentryWrapper.class);
        service = new LogService(wrapperMock);
    }

    @Test
    public void testThatMessageIsForwardedToSentryWrapper() {
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        String message = "DUMMY_MESSAGE";
        
        service.handleMessage(message);
        
        verify(wrapperMock).captureMessage(messageCaptor.capture());
        Assertions.assertEquals(message, messageCaptor.getValue());
    }

    @Test 
    public void testThatExceptionIsForwardedToSentryWrapper() {
        ArgumentCaptor<RuntimeException> exceptionCaptor = ArgumentCaptor.forClass(RuntimeException.class);
        RuntimeException exception = new RuntimeException("DUMMY_MESSAGE");
        
        service.handleException(exception);
        
        verify(wrapperMock).captureException(exceptionCaptor.capture());
        Assertions.assertEquals(exception, exceptionCaptor.getValue());
    }

}
