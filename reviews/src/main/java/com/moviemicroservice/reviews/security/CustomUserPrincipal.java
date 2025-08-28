package com.moviemicroservice.reviews.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class CustomUserPrincipal {
    private final String email;
    private final Long userId;
}
