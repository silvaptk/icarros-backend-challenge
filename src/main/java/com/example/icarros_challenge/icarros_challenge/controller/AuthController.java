package com.example.icarros_challenge.icarros_challenge.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.icarros_challenge.icarros_challenge.application.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


/**
 * This class represents a set of REST API endpoints. Each method 
 * is an API endpoint and all of them are related to authentication 
 * functionalities
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Method that allows clients to validate given password
     * @param request {@link String} representing the password
     * @return {@code true} if the password is valid, {@code false} otherwise
     */
    @Operation(summary = "Route to validate password", description = "It expects a plain-text body containing the password. It will return `true` if the password is valid or `false` otherwise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password validation succeeded")
    })
    @PostMapping("/password-validation")
    public Boolean validatePassword(@RequestBody(required = false) String request) {
        return this.authService.validatePassword(request);
    }
    
}
