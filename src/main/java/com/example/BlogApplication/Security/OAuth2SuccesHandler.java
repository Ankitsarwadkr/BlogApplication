package com.example.BlogApplication.Security;

import com.example.BlogApplication.Dto.LoginResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
@Slf4j
public class OAuth2SuccesHandler implements AuthenticationSuccessHandler {



    private  AuthService authService;
    private final ObjectMapper objectMapper;

    // Constructor
     public OAuth2SuccesHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Autowired
    public void setAuthService(@Lazy AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User= (OAuth2User) authentication.getPrincipal();

        String registrationId=oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        ResponseEntity<LoginResponseDto> loginResponseDto= authService.handleOAuth2Login(oAuth2User,registrationId);

        LoginResponseDto body = loginResponseDto.getBody();
        String redirectUrl = "http://localhost:5500/index.html"
                + "?jwt=" + body.getJwt()
                + "&userId=" + body.getUserId();

        response.sendRedirect(redirectUrl);
    }
}
