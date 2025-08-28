package com.moviemicroservice.reviews.Exceptions;

public class CouldNotAccessResource extends RuntimeException {
    public CouldNotAccessResource(String message) {
        super(message);
    }
}
