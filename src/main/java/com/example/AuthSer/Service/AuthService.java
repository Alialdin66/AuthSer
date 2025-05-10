package com.example.AuthSer.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AuthSer.DTOs.AuthRequest;
import com.example.AuthSer.DTOs.RegisterRequest;
import com.example.AuthSer.Model.User;
import com.example.AuthSer.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
        String role = request.getRole() == null || request.getRole().isBlank() ? "USER" : request.getRole();
    
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
    
        userRepository.save(user);
        return "User registered";
    }
    

    public String login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return jwtService.generateToken(user);
    }
}

