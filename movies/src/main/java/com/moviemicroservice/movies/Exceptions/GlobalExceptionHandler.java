package com.moviemicroservice.movies.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
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
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
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

    @ExceptionHandler(CannotBeNull.class)
    public ResponseEntity<ErrorResponse> handleCannotBeNullException(
            CannotBeNull ex, WebRequest request) {
        log.error("Cannot be null exception: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Cannot Be Null",
                ex.getMessage(),
                getRequestPath(request),
                "CANNOT_BE_NULL"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CouldNotAccessService.class)
    public ResponseEntity<ErrorResponse> handleCouldNotAccessService(
            CouldNotAccessService ex, WebRequest request) {
        log.error("Could not access the external api: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Could not access the external api",
                ex.getMessage(),
                getRequestPath(request),
                "COULD_NOT_ACCESS_SERVICE"
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
