package com.moviemicroservice.users.Services.impl;

import com.moviemicroservice.users.DTOs.LoginRequestDTO;
import com.moviemicroservice.users.DTOs.LoginResponseDTO;
import com.moviemicroservice.users.DTOs.UserDTO;
import com.moviemicroservice.users.DTOs.UserResponseDTO;
import com.moviemicroservice.users.Entities.Roles;
import com.moviemicroservice.users.Entities.Users;
import com.moviemicroservice.users.Exceptions.AuthenticationFailedException;
import com.moviemicroservice.users.Exceptions.ResourceNotFound;
import com.moviemicroservice.users.Repositories.UsersRepository;
import com.moviemicroservice.users.Security.JWTService;
import com.moviemicroservice.users.Services.UserService;
import com.moviemicroservice.users.Mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final JWTService jwtService;

    public UserResponseDTO registerUser (UserDTO userDTO){
        Users user = userMapper.toEntity(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRole(Roles.USER);
        Users savedUser = usersRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public UserResponseDTO getUser(Long id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFound("User not found"));
        return userMapper.toResponseDto(user);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        try {
            Users user = usersRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new ResourceNotFound("User not found"));
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getPassword()
                    )
            );
            if(authentication.isAuthenticated()){
                String token = jwtService.generateToken(authentication);
                return new LoginResponseDTO(user.getEmail(), user.getRole(), token);
            } else {
                throw new AuthenticationFailedException("Authentication Failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream().map(userMapper::toResponseDto).toList();
    }

    @Override
    public Boolean existsById(Long id) {
        log.info("Checking existence of user with ID: {}", id);
        return usersRepository.existsById(id);
    }


}
