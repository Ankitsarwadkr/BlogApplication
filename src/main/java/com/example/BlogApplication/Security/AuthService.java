package com.example.BlogApplication.Security;


import com.example.BlogApplication.Dto.LoginRequestDto;
import com.example.BlogApplication.Dto.LoginResponseDto;
import com.example.BlogApplication.Dto.SignUpRequestDto;
import com.example.BlogApplication.Dto.SignUpResponseDto;
import com.example.BlogApplication.Entity.Type.AuthProviderType;
import com.example.BlogApplication.Entity.Type.Role;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repositry.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword())
        );

       CustomUserDetails userDetails= (CustomUserDetails) authentication.getPrincipal();
       User user=userDetails.getUser();
        String token= authUtil.getAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public User signUpInternal(SignUpRequestDto signupdto, AuthProviderType providerType, String providerId)
    {
        // check email+username of manual Sign up
        if(providerType == AuthProviderType.EMAIL)
        {
            if(userRepository.findByEmail(signupdto.getEmail()).isPresent())
                throw new IllegalArgumentException("Email Already Registered");
            if(userRepository.findByUsername(signupdto.getUsername()).isPresent())
                throw  new IllegalArgumentException("Username Already Taken");
        }
        else
        {
            if (signupdto.getEmail()!=null && userRepository.findByEmail(signupdto.getEmail()).isPresent())
                throw new IllegalArgumentException("Email is Already registered");
        }
        String username=signupdto.getUsername();
        if (username == null || username.isBlank())
        {
            username="user_" + (providerId !=null ? providerId : System.currentTimeMillis());
        }
        User user=User.builder()
                .username(username)
                .email(signupdto.getEmail())
                .password(providerType ==AuthProviderType.EMAIL ? passwordEncoder.encode(signupdto.getPassword()) : null)
                .providerId(providerId)
                .providerType(providerType)
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto)
    {
        User user=signUpInternal(signUpRequestDto,AuthProviderType.EMAIL,null);
        return new SignUpResponseDto(user.getId(), user.getUsername());
    }


    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2Login(OAuth2User oAuth2User, String registrationId) {
        System.out.println("Google OAuth2User attributes:");
        oAuth2User.getAttributes().forEach((key, value) ->
                System.out.println(key + " : " + value)
        );

        AuthProviderType providerType=authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId= authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user=userRepository.findByProviderIdAndProviderType(providerId,providerType).orElse(null);
        String email=oAuth2User.getAttribute("email");
        String name=oAuth2User.getAttribute("name");

        User emaiUser=  (email != null) ? (User) userRepository.findByEmail(email).orElse(null) :null;

        if(user == null && emaiUser == null)
        {
            String username=authUtil.determineUsernameFromOAuth2user(oAuth2User,registrationId,providerId);
            user =signUpInternal(new SignUpRequestDto(username,email,null),providerType,providerId);
        } else if (user==null && emaiUser!=null) {
            emaiUser.setProviderId(providerId);
            emaiUser.setProviderType(providerType);
            userRepository.save(emaiUser);
            user=emaiUser;
        } else if (user!=null) {
            if (email !=null && !email.equals(user.getEmail()))
            {
                user.setEmail(email);
                userRepository.save(user);
            }
            
        }

        String token= authUtil.getAccessToken(user);
        return ResponseEntity.ok(new LoginResponseDto(token, user.getId()));
    }
}
