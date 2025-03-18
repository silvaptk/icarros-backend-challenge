package com.example.icarros_challenge.icarros_challenge.application;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.icarros_challenge.icarros_challenge.dto.ValidatePasswordRequest;
import com.example.icarros_challenge.icarros_challenge.exception.AuthValidator;

@Tag("unit")
public class AuthServiceUnitTests {

    private AuthService service;
    private AuthValidator validatorMock;

    @BeforeEach
    public void setup() {
        validatorMock = mock(AuthValidator.class);
        service = new AuthService(validatorMock);
    }

    @Test
    public void testThatPasswordIsForwardedToValidator() {
        String password = "DUMMY_PASSWORD";
        ValidatePasswordRequest request = new ValidatePasswordRequest(password);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        
        service.validatePassword(request);
        
        verify(validatorMock).validatePassword(passwordCaptor.capture());
        Assertions.assertEquals(password, passwordCaptor.getValue());
    }

    @Test
    public void testThatValidatorCallResultIsReturned() {
        List<Boolean> possibleResults = List.of(true, false);

        for (Boolean expectedResult : possibleResults) {
            String password = "DUMMY_PASSWORD";
            when(validatorMock.validatePassword(password)).thenReturn(expectedResult);
            ValidatePasswordRequest request = new ValidatePasswordRequest(password);

            Boolean gottenResult = service.validatePassword(request);

            Assertions.assertEquals(expectedResult, gottenResult);
        }
    }
}
