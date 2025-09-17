package com.example.BlogApplication.Tests;
import com.example.BlogApplication.Dto.LoginRequestDto;
import com.example.BlogApplication.Dto.LoginResponseDto;
import com.example.BlogApplication.Dto.SignUpRequestDto;
import com.example.BlogApplication.Dto.SignUpResponseDto;
import com.example.BlogApplication.Entity.Type.AuthProviderType;
import com.example.BlogApplication.Entity.Type.Role;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repositry.UserRepository;
import com.example.BlogApplication.Security.AuthService;
import com.example.BlogApplication.Security.AuthUtil;
import com.example.BlogApplication.Security.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
   private  PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthUtil authUtil;

    @Mock
    private OAuth2User oAuth2User;

    @InjectMocks
    private AuthService authService;




    @Test
    void testSignupInternal_Success()
    {
        SignUpRequestDto dto=new SignUpRequestDto();
        dto.setUsername("ankit");
        dto.setEmail("ankit@gmail.com");
        dto.setPassword("password");


        when(userRepository.findByEmail("ankit@gmail.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("ankit")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User savedUser=User.builder()
                .username("ankit")
                .email("ankit@gmail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result=authService.signUpInternal(dto, AuthProviderType.EMAIL,null);

        assertNotNull(result);
        assertEquals("ankit",result.getUsername());
        assertEquals("ankit@gmail.com",result.getEmail());
        assertEquals("encodedPassword",result.getPassword());
        assertEquals(Role.USER,result.getRole());


        verify(userRepository,times(1)).save(any(User.class));
    }
    @Test
    void testSignupInternal_Failure_EmailAlreadyExists()
    {
        SignUpRequestDto dto= new SignUpRequestDto();

        dto.setUsername("virat");
        dto.setEmail("virat@gmail.com");
        dto.setPassword("password");

        User existinguser=User.builder()
                .username("virat")
                .email("virat@gmail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail("virat@gmail.com")).thenReturn(Optional.of(existinguser));
        assertThrows(IllegalArgumentException.class,()->{
            authService.signUpInternal(dto,AuthProviderType.EMAIL,null);
        });

        verify(userRepository,never()).save(any(User.class));
    }

    @Test
    void testSignUpInternal_Failure_UsernameAlreadyExists()
    {
        SignUpRequestDto dto=new SignUpRequestDto();
        dto.setUsername("virat");
        dto.setEmail("virat@gmail.com");
        dto.setPassword("password");

        User existingUser=User.builder()
                .username("virat")
                .email("virat@gmail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail("virat@gmail.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("virat")).thenReturn(Optional.of(existingUser));

        assertThrows(IllegalArgumentException.class,()->{
            authService.signUpInternal(dto,AuthProviderType.EMAIL,null);
        });
        verify(userRepository,never()).save(any(User.class));

    }
    @Test
    void testSignup_Success() {
        SignUpRequestDto dto = new SignUpRequestDto("ankit", "ankit@gmail.com", "password");

        User savedUser = User.builder()
                .id(1L)
                .username("ankit")
                .email("ankit@gmail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail("ankit@gmail.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("ankit")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        SignUpResponseDto response = authService.signup(dto);

        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals("ankit", response.getUsername());

        verify(userRepository, times(1)).save(any(User.class));
    }
    @Test
    void testLogin_Success()
    {
        LoginRequestDto dto=new LoginRequestDto("ankit@gmail.com","password");

        User mockUser= User.builder()
                .id(1L)
                .username("ankit")
                .email("ankit@gmail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        CustomUserDetails customUserDetails=new CustomUserDetails(mockUser);

        Authentication mockAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuth);

        when(mockAuth.getPrincipal()).thenReturn(customUserDetails);
        when(authUtil.getAccessToken(mockUser)).thenReturn("mockToken123");

        LoginResponseDto responseDto=authService.login(dto);
        assertNotNull(responseDto);
        assertEquals("mockToken123",responseDto.getJwt());
        assertEquals(1L,responseDto.getUserId());

        verify(authenticationManager,times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        verify(authUtil,times(1)).getAccessToken(mockUser);


        }

        @Test
    void testLogin_Failure_Invalidcrendentails()
        {
            LoginRequestDto dto=new LoginRequestDto("ankit@gmail.com","wrongpassword");

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Bad Credentails"));

            assertThrows(RuntimeException.class,()-> {
                authService.login(dto);
            });
            verify(authUtil,never()).getAccessToken(any());
        }

    @Test
    void testHandleOAuth2Login_New_User()
    {
         String registrationId="google";
         String providerId="google123";
         String email="newuser@gmail.com";
         String username="newuser";

         when(authUtil.getProviderTypeFromRegistrationId(registrationId)).thenReturn(AuthProviderType.GOOGLE);
         when(authUtil.determineProviderIdFromOAuth2User(oAuth2User,registrationId)).thenReturn(providerId);
         when(oAuth2User.getAttribute("email")).thenReturn(email);
         when(oAuth2User.getAttribute("name")).thenReturn(username);


         when(userRepository.findByProviderIdAndProviderType(providerId,AuthProviderType.GOOGLE)).thenReturn(Optional.empty());
         when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

         User savedUser =User.builder()
                 .id(1l)
                 .username(username)
                 .email(email)
                 .providerId(providerId)
                 .providerType(AuthProviderType.GOOGLE)
                 .role(Role.USER)
                 .build();

         when(userRepository.save(any(User.class))).thenReturn(savedUser);
         when(authUtil.determineUsernameFromOAuth2user(oAuth2User,registrationId,providerId)).thenReturn(username);
         when(authUtil.getAccessToken(any(User.class))).thenReturn("mock-jwt-token");

        ResponseEntity<LoginResponseDto> response=authService.handleOAuth2Login(oAuth2User,registrationId);
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("mock-jwt-token",response.getBody().getJwt());
        assertEquals(1l,response.getBody().getUserId());
         verify(userRepository,times(1)).save(any(User.class));
         verify(authUtil,times(1)).getAccessToken(any(User.class));
    }


    @Test
    void testHandleOauth2Login_LinkExistingEmailUserToProvider()
    {
        String email ="binod@gmail.com";
        String providerId="google123";

        OAuth2User mockOAuth2User =mock(OAuth2User.class);
        when(mockOAuth2User.getAttribute("email")).thenReturn(email);
        when(mockOAuth2User.getAttribute("name")).thenReturn("binod");

        when(authUtil.getProviderTypeFromRegistrationId("google")).thenReturn(AuthProviderType.GOOGLE);
        when(authUtil.determineProviderIdFromOAuth2User(mockOAuth2User,"google")).thenReturn(providerId);

        User existingUser=User.builder()
                .id(1L)
                .username("binod")
                .email(email)
                .providerId(null)
                .providerType(null)
                .build();

        when(userRepository.findByProviderIdAndProviderType(providerId,AuthProviderType.GOOGLE)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

       when(authUtil.getAccessToken(any(User.class))).thenReturn("mockToken");

        ResponseEntity<LoginResponseDto> response=authService.handleOAuth2Login(mockOAuth2User,"google");
        assertNotNull(response);
        assertEquals(200,response.getStatusCode().value());
        assertEquals("mockToken",response.getBody().getJwt());
        assertEquals(1L,response.getBody().getUserId());


        assertEquals(providerId,existingUser.getProviderId());
        assertEquals(AuthProviderType.GOOGLE,existingUser.getProviderType());

        verify(userRepository,times(1)).save(existingUser);
    }




}
