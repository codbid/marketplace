package org.example.marketplace.app.auth.dto;

public record RefreshTokenRequest(String refreshToken, String fingerprint) {}
