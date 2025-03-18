package com.example.icarros_challenge.icarros_challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.icarros_challenge.icarros_challenge.application.AuthService;
import com.example.icarros_challenge.icarros_challenge.dto.ErrorResponse;
import com.example.icarros_challenge.icarros_challenge.dto.ValidatePasswordRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Method that allows clients to validate given password
     * @param request `ValidatePasswordRequest` containing the password to be validated
     * @return `true` if the password is valid, an `ErrorResponse` otherwise
     */
    @Operation(summary = "Route to validate password", description = "It expects a JSON object containing a password string property. It will return `true` if the password is valid or an error otherwise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password is valid"),
        @ApiResponse(
            responseCode = "400", 
            description = "Password is invalid (less than 9 characters, no lowercase letters, no uppercase letters, no numbers, no special characters, repeated characters or unrecognized characters)",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping("/validate-password")
    public Boolean validatePassword(@RequestBody ValidatePasswordRequest request) {
        return this.authService.validatePassword(request);
    }
    
}
