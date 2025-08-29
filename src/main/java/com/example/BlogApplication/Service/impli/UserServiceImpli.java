package com.example.BlogApplication.Service.impli;

import com.example.BlogApplication.Dto.UserRequestDto;
import com.example.BlogApplication.Dto.UserResponseDto;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repositry.UserRepository;
import com.example.BlogApplication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpli implements UserService {

    private final UserRepository userRepository;


    private UserResponseDto mapToDto(User user)
    {
        UserResponseDto dto=new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }
    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user= User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        User saved=userRepository.save(user);
        return mapToDto(saved);

    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(()->new RuntimeException("User Not Found"));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
