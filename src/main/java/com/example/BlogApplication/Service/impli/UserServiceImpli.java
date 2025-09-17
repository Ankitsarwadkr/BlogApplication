package com.example.BlogApplication.Service.impli;

import com.example.BlogApplication.Dto.UserRequestDto;
import com.example.BlogApplication.Dto.UserResponseDto;
import com.example.BlogApplication.Entity.Type.AuthProviderType;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Repositry.UserRepository;
import com.example.BlogApplication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpli implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    private UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRole(user.getRole());

        return dto;
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with this Emal : " + email + "Not Found"));
        return mapToDto(user);
    }

    @Override
    public UserResponseDto updateUserProfile(String email, UserRequestDto updateDto) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User With email id  "+email+" Not Found"));

        if(updateDto.getUsername()!=null && !updateDto.getUsername().isBlank())
        {
            user.setUsername(updateDto.getUsername());
        }
        if(user.getProviderType()== AuthProviderType.EMAIL)
        {
            if(updateDto.getEmail()!=null && !updateDto.getEmail().isBlank())
            {
                user.setEmail(updateDto.getEmail());
            }
            if(updateDto.getPassword()!=null && !updateDto.getPassword().isBlank())
            {
                user.setPassword(passwordEncoder.encode(updateDto.getPassword()));
            }
        }
        User updated=userRepository.save(user);
        return mapToDto(updated);
    }
}