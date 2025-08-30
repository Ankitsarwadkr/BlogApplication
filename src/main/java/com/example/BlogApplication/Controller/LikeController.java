package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Dto.LikeRequestDto;
import com.example.BlogApplication.Dto.LikeResponseDto;
import com.example.BlogApplication.Service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponseDto> addLike(@RequestBody LikeRequestDto dto)
    {
        return ResponseEntity.ok(likeService.addLike(dto));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeResponseDto>> getLikesByPostId(@PathVariable Long postId)
    {
        return ResponseEntity.ok(likeService.getLikesByPostId(postId));
    }
}
