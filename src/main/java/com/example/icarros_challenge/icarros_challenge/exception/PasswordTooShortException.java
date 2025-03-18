package com.example.icarros_challenge.icarros_challenge.exception;

/**
 * This class represents a validation exception to be thrown if 
 * a given password is too short
 */
public class PasswordTooShortException extends ValidationException {

    public PasswordTooShortException(int minLength) {
        super("PASSWORD_TOO_SHORT", "Forneça uma senha com " + minLength + " ou mais caracteres");
    }
    
}
