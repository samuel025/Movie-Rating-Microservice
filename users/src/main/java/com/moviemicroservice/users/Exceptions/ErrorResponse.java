package com.moviemicroservice.users.Exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private String details;
    private String path;
    private String errorCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    // Static constructor for convenience
    public static ErrorResponse of(String message, String details, String path, String errorCode) {
        return ErrorResponse.builder()
                .message(message)
                .details(details)
                .path(path)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
