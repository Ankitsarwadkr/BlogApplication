package com.example.BlogApplication.Config;

import com.example.BlogApplication.Entity.Type.Role;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repositry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin already exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123")) // encode password
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            System.out.println("Admin created successfully!");
        }
    }
}
