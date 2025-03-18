package com.example.icarros_challenge.icarros_challenge.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.icarros_challenge.icarros_challenge.application.AuthService;
import com.example.icarros_challenge.icarros_challenge.dto.ValidatePasswordRequest;

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
        ValidatePasswordRequest request = new ValidatePasswordRequest("DUMMY_PASSWORD");
        ArgumentCaptor<ValidatePasswordRequest> requestCaptor = ArgumentCaptor.forClass(ValidatePasswordRequest.class);
        
        controller.validatePassword(request);
        
        verify(serviceMock).validatePassword(requestCaptor.capture());
        Assertions.assertEquals(request, requestCaptor.getValue());
    }

    @Test
    public void testThatItReturnsTheServiceCallResult() {
        List<Boolean> possibleResults = List.of(true, false);

        for (Boolean expectedResult :  possibleResults) {
            ValidatePasswordRequest request = new ValidatePasswordRequest("DUMMY_PASSWORD");
            when(serviceMock.validatePassword(request)).thenReturn(expectedResult);

            Boolean gottenResult = controller.validatePassword(request);
            
            Assertions.assertEquals(expectedResult, gottenResult);
        }
    }
}
