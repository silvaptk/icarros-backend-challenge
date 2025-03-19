package com.example.icarros_challenge.icarros_challenge.exception;

public class PasswordIsMissingException extends ValidationException {
    public PasswordIsMissingException() {
        super("PASSWORD_IS_MISSING", "Nenhuma senha enviada");
    }
}
