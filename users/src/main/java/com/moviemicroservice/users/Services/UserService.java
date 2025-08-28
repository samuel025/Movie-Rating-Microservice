package com.moviemicroservice.users.Services;


import com.moviemicroservice.users.DTOs.LoginRequestDTO;
import com.moviemicroservice.users.DTOs.LoginResponseDTO;
import com.moviemicroservice.users.DTOs.UserDTO;
import com.moviemicroservice.users.DTOs.UserResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface UserService {
    UserResponseDTO registerUser(UserDTO userDTO);
    UserResponseDTO getUser(Long id);
    List<UserResponseDTO> getAllUsers();
    Boolean existsById(Long id);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}

