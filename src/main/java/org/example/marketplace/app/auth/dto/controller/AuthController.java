package org.example.marketplace.app.auth.dto.controller;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.auth.dto.LoginRequest;
import org.example.marketplace.app.auth.dto.RefreshTokenRequest;
import org.example.marketplace.app.auth.dto.TokenPair;
import org.example.marketplace.app.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenPair login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public TokenPair refresh(@RequestBody RefreshTokenRequest request) {
        return authService.refresh(request);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody RefreshTokenRequest request) {
        authService.logout(request.refreshToken());
    }
}
