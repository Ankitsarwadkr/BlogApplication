package com.example.BlogApplication.Controller;


import com.example.BlogApplication.Dto.LoginRequestDto;
import com.example.BlogApplication.Dto.LoginResponseDto;
import com.example.BlogApplication.Dto.SignUpRequestDto;
import com.example.BlogApplication.Dto.SignUpResponseDto;
import com.example.BlogApplication.Security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for User registration and login")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user",description = "Register a new user with username , email, and password")
    public  ResponseEntity<SignUpResponseDto> register(@RequestBody SignUpRequestDto signupRequestDto)
    {
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @PostMapping("/userlogin")
    @Operation(summary = "login user",description = "Authenticate user with email+password and return jwt token")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto)
    {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
}
