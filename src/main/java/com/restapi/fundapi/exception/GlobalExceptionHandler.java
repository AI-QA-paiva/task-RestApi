package com.restapi.fundapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        String bodyOfResponse = "Invalid argument provided";
        return ResponseEntity.badRequest().body(bodyOfResponse);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        String bodyOfResponse = "An unexpected error occurred";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyOfResponse);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyOfResponse);
    }

    @ExceptionHandler(value = { InvalidEndpointException.class })
    protected ResponseEntity<Object> handleInvalidEndpoint(InvalidEndpointException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyOfResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Itera sobre todos os erros de validação
        ex.getBindingResult().getFieldErrors().forEach(error -> {

            // Obtém o nome do campo e a mensagem de erro
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();

            // Adiciona o erro ao mapa
            errors.put(fieldName, errorMessage);
        });

        // Retorna uma resposta HTTP 400 com o mapa de erros
        return ResponseEntity.badRequest().body(errors);

    }

}
