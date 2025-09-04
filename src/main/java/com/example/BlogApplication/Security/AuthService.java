package com.example.BlogApplication.Security;


import com.example.BlogApplication.Dto.LoginRequestDto;
import com.example.BlogApplication.Dto.LoginResponseDto;
import com.example.BlogApplication.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final AuthUtil authUtil;
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );

       CustomUserDetails userDetails= (CustomUserDetails) authentication.getPrincipal();
       User user=userDetails.getUser();
        String token= authUtil.getAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }
}
