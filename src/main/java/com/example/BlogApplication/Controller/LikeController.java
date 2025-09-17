package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Dto.LikeRequestDto;
import com.example.BlogApplication.Dto.LikeResponseDto;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Security.CustomUserDetails;
import com.example.BlogApplication.Service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponseDto> toggleLike(@RequestBody LikeRequestDto likeRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails)
    {
        User loggedUser= customUserDetails.getUser();
        return  ResponseEntity.ok(likeService.toggleLike(likeRequestDto,loggedUser));
    }

}
