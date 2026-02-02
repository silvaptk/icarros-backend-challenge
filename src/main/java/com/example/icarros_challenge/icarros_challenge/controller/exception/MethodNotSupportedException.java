package com.example.icarros_challenge.icarros_challenge.controller.exception;

/**
 * Exception to be thrown when the user tries to request a resource with
 * an HTTP method that isn't supported
 */
public class MethodNotSupportedException extends DefaultException {

    public MethodNotSupportedException() {
        super("METHOD_NOT_SUPPORTED", "O método da requisição está incorreto. Reveja a documentação e tente novamente");
    }

}
