package com.example.BlogApplication.Service;

import com.example.BlogApplication.Dto.LikeRequestDto;
import com.example.BlogApplication.Dto.LikeResponseDto;
import com.example.BlogApplication.Entity.User;

import java.util.List;

public interface LikeService {

    LikeResponseDto toggleLike(LikeRequestDto likeRequestDto, User loggedUser);
}
