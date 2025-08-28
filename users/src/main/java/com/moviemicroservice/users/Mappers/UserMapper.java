package com.moviemicroservice.users.Mappers;

import com.moviemicroservice.users.DTOs.UserDTO;
import com.moviemicroservice.users.DTOs.UserResponseDTO;
import com.moviemicroservice.users.Entities.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public Users toEntity(UserDTO userDTO) {
        return Users.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();
    }

    public UserResponseDTO toResponseDto (Users user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }


}
