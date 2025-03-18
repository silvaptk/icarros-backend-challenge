package com.example.icarros_challenge.icarros_challenge.exception;

/**
 * This class represents a validation exception to be thrown if 
 * a given password has no numbers
 */
public class PasswordWithoutNumbersException extends ValidationException {

    public PasswordWithoutNumbersException() {
        super("PASSWORD_WITHOUT_NUMBERS", "Forneça uma senha com pelo menos um número");
    }
    
}
