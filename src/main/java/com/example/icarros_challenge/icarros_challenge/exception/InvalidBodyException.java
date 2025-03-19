package com.example.icarros_challenge.icarros_challenge.exception;

public class InvalidBodyException extends ValidationException {
    public InvalidBodyException() {
        super("INVALID_BODY", "O corpo da requisição é inválido. JSON com propriedade `password` esperado");
    }
}
