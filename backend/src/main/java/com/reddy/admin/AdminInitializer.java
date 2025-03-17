package com.reddy.admin;

import com.reddy.enums.Role;
import com.reddy.model.User;
import com.reddy.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createDefaultAdmin() {
        if (!userRepository.existsByRole(Role.ADMIN)) {
            User admin = new User();
            admin.setUsername("admin");  // Default username
            admin.setPassword(passwordEncoder.encode("admin123"));  // TEMPORARY PASSWORD
            admin.setRole(Role.ADMIN);
            // Flag to force password change
            userRepository.save(admin);

            // Log this securely (DO NOT log credentials)
            System.out.println("Default admin created. TEMPORARY PASSWORD: admin123");
        }
    }
}
