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
import com.example.icarros_challenge.icarros_challenge.exception.AuthValidator;

@Tag("integration")
public class AuthControllerIntegrationTests {

    private AuthController controller;

    @BeforeEach
    public void setup() {
        this.customSetup(this.getServiceMock());
    }

    @Test
    public void testPasswordForwardingToService() {
        AuthValidator validatorMock = mock(AuthValidator.class);
        this.customSetup(new AuthService(validatorMock));
        String password = "DUMMY_PASSWORD";
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        
        controller.validatePassword(new ValidatePasswordRequest(password));
        
        verify(validatorMock).validatePassword(passwordCaptor.capture());
        Assertions.assertEquals(password, passwordCaptor.getValue());
    }

    @Test
    public void testThatItReturnsTheValueGottenFromServiceCall() {
        AuthValidator validatorMock = mock(AuthValidator.class);
        this.customSetup(new AuthService(validatorMock));
        String password = "DUMMY_PASSWORD";
        List<Boolean> possibleResults = List.of(true, false);

        for (Boolean expectedResult : possibleResults) {
            when(validatorMock.validatePassword(password)).thenReturn(expectedResult);

            Boolean gottenResult = controller.validatePassword(new ValidatePasswordRequest(password));

            Assertions.assertEquals(expectedResult, gottenResult);
        }
    }

    private AuthService getServiceMock() {
        return mock(AuthService.class);
    }

    private void customSetup(AuthService service) {
        controller = new AuthController(service);
    }

}
