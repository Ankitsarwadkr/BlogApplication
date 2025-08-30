package com.example.BlogApplication.Service;

import com.example.BlogApplication.Dto.LikeRequestDto;
import com.example.BlogApplication.Dto.LikeResponseDto;

import java.util.List;

public interface LikeService {
    LikeResponseDto addLike(LikeRequestDto dto);

    List<LikeResponseDto> getLikesByPostId(Long postId);
}
