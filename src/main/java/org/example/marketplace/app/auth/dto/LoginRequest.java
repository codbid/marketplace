package org.example.marketplace.app.auth.dto;

public record LoginRequest(String email, String password, String fingerprint) {}
