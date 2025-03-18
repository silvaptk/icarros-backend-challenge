package com.example.icarros_challenge.icarros_challenge.exception;

/**
 * This class represents a validation exception to be thrown if 
 * a given password has repeated characters
 */
public class PasswordWithRepeatedCharactersException extends ValidationException {

    public PasswordWithRepeatedCharactersException() {
        super("PASSWORD_WITH_REPEATED_CHARACTERS", "Forneça uma senha sem caracteres repetidos");
    }
    
}
