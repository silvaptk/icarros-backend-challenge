package com.example.icarros_challenge.icarros_challenge.unit.application;

import java.util.List;

import com.example.icarros_challenge.icarros_challenge.application.AuthService;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Tag("unit")
public class AuthServiceUnitTests {

    private AuthService service;
    private PasswordValidator validatorMock;

    @BeforeEach
    public void setup() {
        validatorMock = mock(PasswordValidator.class);
        service = new AuthService(validatorMock);
    }

    @Test
    public void testThatPasswordIsForwardedToValidator() {
        String password = "DUMMY_PASSWORD";
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        
        service.validatePassword(password);
        
        verify(validatorMock).validate(passwordCaptor.capture());
        Assertions.assertEquals(password, passwordCaptor.getValue());
    }

    @Test
    public void testThatValidatorCallResultIsReturned() {
        List<Boolean> possibleResults = List.of(true, false);

        for (Boolean expectedResult : possibleResults) {
            String password = "DUMMY_PASSWORD";
            when(validatorMock.validate(password)).thenReturn(expectedResult);

            Boolean gottenResult = service.validatePassword(password);

            Assertions.assertEquals(expectedResult, gottenResult);
        }
    }
}
