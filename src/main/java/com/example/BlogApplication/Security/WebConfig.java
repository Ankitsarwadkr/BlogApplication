package com.example.BlogApplication.Security;

import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repositry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class WebConfig {


        private final CustomeUserDetailService customeUserDetailService;
        private  final PasswordEncoder passwordEncoder;
        private final JwtAuthFilter jwtAuthFilter;

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider()
        {
            DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(customeUserDetailService);
            authenticationProvider.setPasswordEncoder(passwordEncoder);
            return authenticationProvider;
        }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf->csrf.disable())
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                .requestMatchers("/api/users","/api/auth/**").permitAll()
                        .requestMatchers("/api/users/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }



}
