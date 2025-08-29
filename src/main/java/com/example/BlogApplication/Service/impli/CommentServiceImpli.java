package com.example.BlogApplication.Service.impli;

import com.example.BlogApplication.Dto.CommentRequestDto;
import com.example.BlogApplication.Dto.CommentResponseDto;
import com.example.BlogApplication.Entity.Comment;
import com.example.BlogApplication.Entity.Post;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repositry.CommentRepository;
import com.example.BlogApplication.Repositry.PostRepository;
import com.example.BlogApplication.Repositry.UserRepository;
import com.example.BlogApplication.Service.CommentService;
import com.example.BlogApplication.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpli implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private CommentResponseDto mapToDto(Comment comment)
    {
        CommentResponseDto dto =new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUserName(comment.getUser().getUsername());
        dto.setPostTitle(comment.getPost().getTitle());
        dto.setCreatedAt(comment.getCreatedAt());

        return dto;
    }

    @Override
    public CommentResponseDto addComment(CommentRequestDto dto) {
        User user=userRepository.findById(dto.getUserId())
                .orElseThrow(()->new RuntimeException("User Not Found"));

        Post post=postRepository.findById(dto.getPostId())
                .orElseThrow(()-> new RuntimeException("POst Not Found"));
        Comment comment =Comment.builder()
                .content(dto.getContent())
                .user(user)
                .post(post)
                .build();
        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> getCommentById(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::mapToDto).
                collect(Collectors.toList());
    }
}
