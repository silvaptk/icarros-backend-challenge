package com.example.icarros_challenge.icarros_challenge.application;

import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.springframework.stereotype.Service;


/**
 * Encapsulates authentication-related functionalities
 */
@Service
public class AuthService {

    private final PasswordValidator passwordValidator;

    public AuthService(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    /**
     * This function validates the provided password against the 
     * business-defined rules
     * @param password The password to be validated
     * @return `true` if the password is valid, `false` otherwise
     */
    public Boolean validatePassword(String password) {
        return this.passwordValidator.validate(password);
    }
    
}
