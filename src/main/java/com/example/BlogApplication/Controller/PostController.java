package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Dto.PostRequestDto;
import com.example.BlogApplication.Dto.PostResponseDto;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Security.CustomUserDetails;
import com.example.BlogApplication.Service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/posts")
public class PostController {
    private final PostService postService;

    @Operation(
            summary = "Create a new post",
            description = "Allows an authenticated user to create a new blog post.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - JWT required")
            }
    )
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid  @RequestBody PostRequestDto dto , @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        User loggedInUser= userDetails.getUser();
        return ResponseEntity.ok(postService.createPost(dto,loggedInUser));
    }

    @Operation(
            summary = "Get post by ID",
            description = "Fetches a single blog post by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post found"),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostByID(@PathVariable Long id)
    {
        return ResponseEntity.ok(postService.getPostByID(id));
    }

    @Operation(
            summary = "Get all posts",
            description = "Fetches all blog posts in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
            }
    )
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts()
    {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @Operation(
            summary = "Delete a post",
            description = "Deletes a post owned by the authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post deleted successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Not the post owner"),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deltepost(@PathVariable Long id , @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        postService.deletePost(id,userDetails.getUser());
        return ResponseEntity.ok("Post Deleted SuccesFully");
    }

}
