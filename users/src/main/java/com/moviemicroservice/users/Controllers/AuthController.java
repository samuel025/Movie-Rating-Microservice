package com.moviemicroservice.users.Controllers;


import com.moviemicroservice.users.DTOs.LoginRequestDTO;
import com.moviemicroservice.users.DTOs.LoginResponseDTO;
import com.moviemicroservice.users.DTOs.UserDTO;
import com.moviemicroservice.users.DTOs.UserResponseDTO;
import com.moviemicroservice.users.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(service.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(service.login(loginRequestDTO));
    }
}
