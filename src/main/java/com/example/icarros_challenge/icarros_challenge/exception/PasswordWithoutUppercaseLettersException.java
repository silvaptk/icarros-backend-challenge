package com.example.icarros_challenge.icarros_challenge.exception;

/**
 * This class represents a validation exception to be thrown if 
 * a given password has no uppercase letters
 */
public class PasswordWithoutUppercaseLettersException extends ValidationException {

    public PasswordWithoutUppercaseLettersException() {
        super("PASSWORD_WITHOUT_UPPERCASE_LETTERS", "Forneça uma senha com pelo menos uma letra maiúscula");
    }

}
