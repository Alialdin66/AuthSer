package com.example.AuthSer.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.AuthSer.Model.User;
import com.example.AuthSer.Repository.UserRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123")) // كلمة سر قوية في production
                        .role("ADMIN")
                        .build();
                userRepository.save(admin);
                System.out.println("Admin user created.");
            }
        };
    }
}

