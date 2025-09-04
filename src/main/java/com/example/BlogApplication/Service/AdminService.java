package com.example.BlogApplication.Service;

import com.example.BlogApplication.Dto.PostResponseDto;
import com.example.BlogApplication.Dto.UserResponseDto;

import java.util.List;

public interface AdminService {
    List<UserResponseDto> getAllUsers();

    void deleteUser(Long id);

    List<PostResponseDto> getPosts();

    void deletePost(Long id);
}
