package com.example.icarros_challenge.icarros_challenge.exception;

/**
 * This class represents a validation exception to be thrown if 
 * a given password has an unexpected character
 */
public class PasswordWithUnexpectedCharacterException extends ValidationException {

    public PasswordWithUnexpectedCharacterException(String characters) {
        super("PASSWORD_WITH_UNEXPECTED_CHARACTER", "Foram encontrados caracteres não permitidos: \"" + characters + "\"");
    }
    
}
