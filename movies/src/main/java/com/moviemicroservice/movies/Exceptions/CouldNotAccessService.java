package com.moviemicroservice.movies.Exceptions;

public class CouldNotAccessService extends RuntimeException {
    public CouldNotAccessService(String message) {
        super(message);
    }
}
