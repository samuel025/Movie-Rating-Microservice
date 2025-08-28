package com.moviemicroservice.users.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRequestDTO {
    private String email;
    private String password;
}
