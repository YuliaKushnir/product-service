package org.example.productservice.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.exceptions.CreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException and returns 400 Bad Request
     * @param e the exception
     * @return error response with error information
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("IllegalArgumentException thrown: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * Handles EntityNotFoundException and returns 404 Not Found
     * @param e the exception
     * @return error response with error information
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
        log.warn("Entity not found: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /**
     * Handles CreationException and returns 500 INternal Server Error
     * @param e the exception
     * @return error response with error information
     */
    @ExceptionHandler(CreationException.class)
    protected ResponseEntity<Object> handlePostCreationException(CreationException e) {
        log.error("Product creation failed: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * Handles MethodArgumentNotValidException and returns 400 Bad Request
     * @param e the exception
     * @return error response with error information
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("Validation failed: {}", e.getMessage());

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                String.join("; ", errors)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private static ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponse response = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), message);
        return ResponseEntity.status(httpStatus.value()).body(response);
    }

    @Getter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class ErrorResponse {
        private int status;
        private String error;
        private String message;
    }
}