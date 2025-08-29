package com.example.BlogApplication.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private String userName;
    private String postTitle;
    private LocalDateTime createdAt;
}
