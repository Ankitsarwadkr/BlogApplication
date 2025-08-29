package com.example.BlogApplication.Service;

import com.example.BlogApplication.Dto.CommentRequestDto;
import com.example.BlogApplication.Dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto addComment(CommentRequestDto dto);

    List<CommentResponseDto> getCommentById(Long postId);
}
