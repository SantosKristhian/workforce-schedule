package com.shiftflow.security.controller;

import com.shiftflow.security.dto.AuthRequest;
import com.shiftflow.security.dto.AuthResponse;
import com.shiftflow.security.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
