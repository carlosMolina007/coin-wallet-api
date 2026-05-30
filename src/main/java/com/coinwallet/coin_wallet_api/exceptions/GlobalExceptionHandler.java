package com.coinwallet.coin_wallet_api.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<Map<String, String>> handleResourceBadRequestException(ResourceBadRequestException exception){

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Error", exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException exception){

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Error", exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }


}
