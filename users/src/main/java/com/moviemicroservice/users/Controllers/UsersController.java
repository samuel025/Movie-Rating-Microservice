package com.moviemicroservice.users.Controllers;


import com.moviemicroservice.users.DTOs.UserDTO;
import com.moviemicroservice.users.DTOs.UserResponseDTO;
import com.moviemicroservice.users.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UserService service;



    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getUser(id));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

}
