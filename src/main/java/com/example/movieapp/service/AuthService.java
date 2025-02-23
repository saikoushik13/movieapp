package com.example.movieapp.service;

import com.example.movieapp.dto.AuthResponse;
import com.example.movieapp.dto.LoginRequest;
import com.example.movieapp.dto.SignupRequest;
import com.example.movieapp.model.Role;
import com.example.movieapp.model.User;
import com.example.movieapp.repository.UserRepository;
import com.example.movieapp.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public AuthResponse register(SignupRequest request) {
        // Validate if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }

        // Validate role (Assign default ROLE_USER if not provided)
        if (request.getRole() == null) {
            request.setRole(Role.ROLE_USER);  // Default role
        }

        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hashing password
        user.setRole(request.getRole());

        // Save user to database
        userRepository.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request){
        Optional<User> userOpt=userRepository.findByEmail(request.getEmail());
        if(userOpt.isPresent()&& passwordEncoder.matches(request.getPassword(),userOpt.get().getPassword())){
            String token= jwtUtil.generateToken(userOpt.get().getEmail(),userOpt.get().getRole().name());
            return new AuthResponse(token);
        }
        else {
            throw new RuntimeException("Invalid credentials");
        }

    }
}
