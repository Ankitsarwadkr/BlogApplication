package com.example.BlogApplication.Service;

import com.example.BlogApplication.Dto.UserRequestDto;
import com.example.BlogApplication.Dto.UserResponseDto;

public interface UserService {

    UserResponseDto getUserByEmail(String email);

    UserResponseDto updateUserProfile(String email, UserRequestDto updateDto);
}
