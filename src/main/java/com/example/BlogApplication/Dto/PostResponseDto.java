package com.example.BlogApplication.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    @Schema(example = "1", description = "Unique post identifier")
    private Long id;

    @Schema(example = "My First Blog Post", description = "Post title")
    private String title;

    @Schema(example = "This is my content", description = "Post body text")
    private String content;

    @Schema(example = "Virat", description = "Author username")
    private String authorname;

    @Schema(example = "Technology", description = "Category of the blog")
    private String category;

    @Schema(example = "2025-09-11T14:30:00", description = "Created timestamp")
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;
    private int likeCount;



}
