package com.example.movieapp.dto;

import com.example.movieapp.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String password;
    private Role role;
}

