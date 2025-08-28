package com.moviemicroservice.movies.Exceptions;

public class CannotBeNull extends RuntimeException {
    public CannotBeNull(String message) {
        super(message);
    }
}
