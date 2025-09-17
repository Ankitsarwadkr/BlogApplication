package com.example.BlogApplication.Service;

import com.example.BlogApplication.Dto.CommentRequestDto;
import com.example.BlogApplication.Dto.CommentResponseDto;
import com.example.BlogApplication.Entity.User;

import java.util.List;

public interface CommentService {
   

    List<CommentResponseDto> getCommentById(Long postId);

    CommentResponseDto addComment(CommentRequestDto commentRequestDto, User loggedUser);
}
