package com.example.icarros_challenge.icarros_challenge.dto;

import java.util.List;

/**
 * This class represents a set of error responses to be returned to the client
 */
public record ErrorsResponse(List<ErrorResponse> errors) {

}
