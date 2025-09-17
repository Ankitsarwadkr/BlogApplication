package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Dto.UserRequestDto;
import com.example.BlogApplication.Dto.UserResponseDto;
import com.example.BlogApplication.Security.CustomUserDetails;
import com.example.BlogApplication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyProfile(Authentication authentication)
    {
        String email= authentication.getName();
        return  ResponseEntity.ok(userService.getUserByEmail(email));
    }
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                           @RequestBody UserRequestDto updateDto )
    {
        return ResponseEntity.ok(userService.updateUserProfile(userDetails.getUsername(),updateDto));
    }

}