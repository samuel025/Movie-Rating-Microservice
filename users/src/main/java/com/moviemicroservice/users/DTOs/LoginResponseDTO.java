package com.moviemicroservice.users.DTOs;


import com.moviemicroservice.users.Entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private String email;
    private Roles role;
    private  String token;
}
