package com.example.icarros_challenge.icarros_challenge.exception;

/**
 * This class represents a validation exception to be thrown if 
 * a given password has no lowercase letters
 */
public class PasswordWithoutLowercaseLettersException extends ValidationException {

    public PasswordWithoutLowercaseLettersException() {
        super("PASSWORD_WITHOUT_LOWERCASE_LETTERS", "Forneça uma senha com pelo menos uma letra minúscula");
    }
    
}
