package com.example.icarros_challenge.icarros_challenge.unit.controller;

import java.util.List;

import com.example.icarros_challenge.icarros_challenge.controller.AuthController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.icarros_challenge.icarros_challenge.application.AuthService;

@Tag("unit")
public class AuthControllerUnitTests {
    private AuthController controller;
    private AuthService serviceMock;

    @BeforeEach
    public void setup() {
        serviceMock = mock(AuthService.class);
        controller = new AuthController(serviceMock);
    }

    @Test
    public void testRequestBodyForwardingToService() {
        String password = "DUMMY_PASSWORD";
        ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
        
        controller.validatePassword(password);
        
        verify(serviceMock).validatePassword(requestCaptor.capture());
        Assertions.assertEquals(password, requestCaptor.getValue());
    }

    @Test
    public void testThatItReturnsTheServiceCallResult() {
        List<Boolean> possibleResults = List.of(true, false);

        for (Boolean expectedResult :  possibleResults) {
            String password = "DUMMY_PASSWORD";
            when(serviceMock.validatePassword(password)).thenReturn(expectedResult);

            Boolean gottenResult = controller.validatePassword(password);
            
            Assertions.assertEquals(expectedResult, gottenResult);
        }
    }
}
