package com.example.icarros_challenge.icarros_challenge.controller.exception;

/**
 * Exception to be thrown when the client requests a resource that
 * does not exist
 */
public class ResourceNotFoundException extends DefaultException {

    public ResourceNotFoundException() {
        super("RESOURCE_NOT_FOUND", "O recurso solicitado não foi encontrado. Reveja a documentação e tente novamente");
    }

}
