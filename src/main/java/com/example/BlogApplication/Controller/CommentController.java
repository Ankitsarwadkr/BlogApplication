package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Dto.CommentRequestDto;
import com.example.BlogApplication.Dto.CommentResponseDto;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Security.CustomUserDetails;
import com.example.BlogApplication.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/comments")
public class CommentController {
    private final CommentService commentService;
   @PostMapping
   public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails)
   {
       User loggedUser=customUserDetails.getUser();
       return ResponseEntity.ok(commentService.addComment(commentRequestDto,loggedUser));
   }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long postId)
    {
        return  ResponseEntity.ok(commentService.getCommentById(postId));
    }
}
