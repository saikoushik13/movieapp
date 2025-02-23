package com.example.movieapp.controller;

import com.example.movieapp.dto.AuthResponse;
import com.example.movieapp.dto.LoginRequest;
import com.example.movieapp.dto.SignupRequest;
import com.example.movieapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody SignupRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
