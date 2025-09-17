package com.example.BlogApplication.Service;

import com.example.BlogApplication.Dto.PostRequestDto;
import com.example.BlogApplication.Dto.PostResponseDto;
import com.example.BlogApplication.Entity.User;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostRequestDto dto, User loggedInUser);

    PostResponseDto getPostByID(Long id);

    List<PostResponseDto> getAllPost();

    void deletePost(Long id, User user);
}
