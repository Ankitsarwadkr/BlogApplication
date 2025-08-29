package com.example.BlogApplication.Service;

import com.example.BlogApplication.Dto.UserRequestDto;
import com.example.BlogApplication.Dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    void deleteUser(Long id);
}
