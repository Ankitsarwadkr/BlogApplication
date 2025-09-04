package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Dto.CommentRequestDto;
import com.example.BlogApplication.Dto.CommentResponseDto;
import com.example.BlogApplication.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/comments")
public class CommentController {
    private final CommentService commentService;
     @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto dto)
     {
         return ResponseEntity.ok(commentService.addComment(dto));
     }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long postId)
    {
        return  ResponseEntity.ok(commentService.getCommentById(postId));
    }
}
