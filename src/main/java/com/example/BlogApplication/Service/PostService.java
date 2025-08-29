package com.example.BlogApplication.Service;

import com.example.BlogApplication.Dto.PostRequestDto;
import com.example.BlogApplication.Dto.PostResponseDto;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostRequestDto dto);

    PostResponseDto getPostByID(Long id);

    List<PostResponseDto> getAllPost();

    void deletePost(Long id);
}
