package com.shiftflow.security.dto;

public record AuthRequest(
        String email,
        String password
) {}
