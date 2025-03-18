package com.example.icarros_challenge.icarros_challenge.exception;

/**
 * This class represents a validation exception to be thrown if 
 * a given password has no special characters
 */
public class PasswordWithoutSpecialCharactersException extends ValidationException {

    public PasswordWithoutSpecialCharactersException() {
        super("PASSWORD_WITHOUT_SPECIAL_CHARACTERS", "Forneça uma senha com ao menos um caractere especial (\"!@#$%^&*()-+\")");
    }
}
