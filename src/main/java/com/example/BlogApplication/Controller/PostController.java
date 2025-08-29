package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Dto.PostRequestDto;
import com.example.BlogApplication.Dto.PostResponseDto;
import com.example.BlogApplication.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;


    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto dto)
    {
        return ResponseEntity.ok(postService.createPost(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostByID(@PathVariable Long id)
    {
        return ResponseEntity.ok(postService.getPostByID(id));
    }
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPost()
    {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deltepost(@PathVariable Long id)
    {
        postService.deletePost(id);
        return ResponseEntity.ok("Post Deleted SuccesFully");
    }

}
