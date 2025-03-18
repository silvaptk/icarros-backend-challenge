package com.example.icarros_challenge.icarros_challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.icarros_challenge.icarros_challenge.exception.AuthValidator;

/**
 * Validation configuration class
 */
@Configuration
public class ValidationConfig {

    /**
     * This method allows Spring to inject the `AuthValidator` class
     * automatically
     * 
     * @return AuthValidator class that encapsulates the validation 
     * rules related to authentication 
     */
    @Bean
    public AuthValidator authValidator() {
        return new AuthValidator();
    }
    
}
