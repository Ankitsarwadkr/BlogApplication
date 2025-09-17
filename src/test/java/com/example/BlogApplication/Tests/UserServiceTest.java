package com.example.BlogApplication.Tests;

import com.example.BlogApplication.Dto.UserResponseDto;
import com.example.BlogApplication.Entity.Type.AuthProviderType;
import com.example.BlogApplication.Entity.Type.Role;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repositry.UserRepository;
import com.example.BlogApplication.Service.impli.UserServiceImpli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpli userServiceImpli;

    @Test
    void testGetUserByEmail_Success()
    {
        String email="virat@gmail.com";
        User user=User.builder()
                .id(1L)
                .username("virat")
                .email(email)
                .role(Role.USER)
                .providerType(AuthProviderType.EMAIL)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserResponseDto result=userServiceImpli.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(1L,result.getId());
        assertEquals("virat",result.getUsername());
        assertEquals(email,result.getEmail());
        assertEquals(Role.USER,result.getRole());

        verify(userRepository,times(1)).findByEmail(email);
    }
}
