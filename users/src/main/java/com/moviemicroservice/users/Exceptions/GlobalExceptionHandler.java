package com.moviemicroservice.users.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFound ex, WebRequest request) {
        log.error("Resource not found exception: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Resource Not Found",
                ex.getMessage(),
                getRequestPath(request),
                "RESOURCE_NOT_FOUND"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleCannotBeNullException(
            AuthenticationFailedException ex, WebRequest request) {
        log.error("Could not find user: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Authentication Failed",
                ex.getMessage(),
                getRequestPath(request),
                "AUTHENTICATION_FAILED"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleCouldNotAccessService(
            IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument passed: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Illegal Argument",
                ex.getMessage(),
                getRequestPath(request),
                "ILLEGAL_ARGUMENT"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }


    private String getRequestPath(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            return ((ServletWebRequest) request).getRequest().getRequestURI();
        }
        return "";
    }
}
