package com.example.BlogApplication.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "Title is required")
    @Size(max = 100,message = "Title cannot exceed 100 charcter")
    @Schema(example = "My first Blog Post")
    private String title;
    @NotBlank(message = "Content is required")
    @Schema(example = "This is the content of my very first blog")
    private String content;

}
