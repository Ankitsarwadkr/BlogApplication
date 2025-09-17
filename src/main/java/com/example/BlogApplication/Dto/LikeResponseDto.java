package com.example.BlogApplication.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDto {

    private Long postId;
    private boolean liked;
    private int totalLikes;
}
