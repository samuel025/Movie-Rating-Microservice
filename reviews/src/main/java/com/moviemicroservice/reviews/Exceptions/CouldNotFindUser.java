package com.moviemicroservice.reviews.Exceptions;

public class CouldNotFindUser extends RuntimeException {
    public CouldNotFindUser(String message) {
        super(message);
    }
}
