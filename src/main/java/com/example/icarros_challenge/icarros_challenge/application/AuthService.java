package com.example.icarros_challenge.icarros_challenge.application;

import org.springframework.stereotype.Service;

import com.example.icarros_challenge.icarros_challenge.dto.ValidatePasswordRequest;
import com.example.icarros_challenge.icarros_challenge.exception.AuthValidator;

/**
 * AuthService encapsulates authentication-related functionalities
 */
@Service
public class AuthService {

    private final AuthValidator authValidator;

    public AuthService(AuthValidator authValidator) {
        this.authValidator = authValidator;
    }

    /**
     * This function validates the provided password against the 
     * business-defined rules
     * @param request The password to bev validated
     * @return wether the given password is valid or not
     */
    public Boolean validatePassword(ValidatePasswordRequest request) {
        String password = request.password();

        return this.authValidator.validatePassword(password);
    }
    
}
