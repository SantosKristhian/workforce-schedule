package com.shiftflow.security.service;

import com.shiftflow.security.dto.AuthRequest;
import com.shiftflow.security.dto.AuthResponse;
import com.shiftflow.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {

        var authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                );

        var authentication =
                authenticationManager.authenticate(authenticationToken);

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}
