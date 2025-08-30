package com.example.BlogApplication.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDto {
    private Long id;
    private String username;
    private Long postId;
    private LocalDateTime createdAt;
}
