package com.example.BlogApplication.Security;

import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Exception.JwtAccessDeniedHandler;
import com.example.BlogApplication.Exception.JwtAuthenticationEntryPoint;
import com.example.BlogApplication.Repositry.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebConfig {


        private final CustomeUserDetailService customeUserDetailService;
        private  final PasswordEncoder passwordEncoder;
        private final JwtAuthFilter jwtAuthFilter;
        private final OAuth2SuccesHandler oAuth2SuccesHandler;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



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
        httpSecurity

                .csrf(csrf->csrf.disable())
                .cors(cors -> {})
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/docs/**", "/api-docs/**").permitAll()
                        .requestMatchers("/api/users/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex->ex
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())//401 unauthorized Handler
                        .accessDeniedHandler(new JwtAccessDeniedHandler()) // 403 Access Denied Handler
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2->oAuth2.failureHandler(
                        (request, response, exception) -> {
                        log.error("Oauth2 error: {}", exception.getMessage());
                               })
                        .successHandler(oAuth2SuccesHandler));

        return httpSecurity.build();
    }



}
