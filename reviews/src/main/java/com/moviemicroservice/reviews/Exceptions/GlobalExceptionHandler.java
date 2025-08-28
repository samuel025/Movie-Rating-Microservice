package com.moviemicroservice.reviews.Exceptions;

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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        log.error("Resource not found exception: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Resource Not Found",
                ex.getMessage(),
                getRequestPath(request),
                "RESOURCE_NOT_FOUND"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CouldNotFindUser.class)
    public ResponseEntity<ErrorResponse> handleCannotBeNullException(
            CouldNotFindUser ex, WebRequest request) {
        log.error("Could not find user: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Could not find user",
                ex.getMessage(),
                getRequestPath(request),
                "COULD_NOT_FIND_USER"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CouldNotAccessResource.class)
    public ResponseEntity<ErrorResponse> handleCouldNotAccessResource(
            CouldNotAccessResource ex, WebRequest request) {
        log.error("Could not access the resource: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Could not access resource",
                ex.getMessage(),
                getRequestPath(request),
                "COULD_NOT_ACCESS_RESOURCE"
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
