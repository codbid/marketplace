package org.example.marketplace.app.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.auth.dto.LoginRequest;
import org.example.marketplace.app.auth.dto.RefreshTokenRequest;
import org.example.marketplace.app.auth.dto.TokenPair;
import org.example.marketplace.app.auth.exception.AuthException;
import org.example.marketplace.app.auth.model.RefreshTokenEntity;
import org.example.marketplace.app.auth.repository.RefreshTokenRepository;
import org.example.marketplace.app.auth.security.JwtService;
import org.example.marketplace.app.auth.security.UserPrincipal;
import org.example.marketplace.app.users.exception.UserException;
import org.example.marketplace.app.users.model.UserEntity;
import org.example.marketplace.app.users.repository.UserRepository;
import org.example.marketplace.common.exception.ApiException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenPair login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserEntity user = userRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new ApiException(UserException.user_not_found));
        UserPrincipal principal = UserPrincipal.fromUser(user);

        String accessToken = jwtService.generateAccessToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal);

        RefreshTokenEntity refreshEntity = new RefreshTokenEntity();
        refreshEntity.setUser(user);
        refreshEntity.setFingerprint(request.fingerprint());
        refreshEntity.setToken(refreshToken);
        refreshEntity.setExpiresAt(Instant.now().plus(Duration.ofDays(jwtService.getRefreshDays())));
        refreshTokenRepository.save(refreshEntity);

        return new TokenPair(accessToken, refreshToken);
    }

    public TokenPair refresh(RefreshTokenRequest request) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new ApiException(AuthException.unauthorized));

        if (refreshTokenEntity.isRevoked() || refreshTokenEntity.getExpiresAt().isBefore(Instant.now()))
            throw new ApiException(AuthException.unauthorized);

        refreshTokenEntity.setRevoked(true);
        refreshTokenRepository.save(refreshTokenEntity);

        if (!refreshTokenEntity.getFingerprint().equalsIgnoreCase(request.fingerprint()))
            throw new ApiException(AuthException.unauthorized);

        UserPrincipal principal = UserPrincipal.fromUser(refreshTokenEntity.getUser());

        String newAccessToken = jwtService.generateAccessToken(principal);
        String newRefreshToken = jwtService.generateRefreshToken(principal);

        RefreshTokenEntity newRefreshTokenEntity = new RefreshTokenEntity();
        newRefreshTokenEntity.setToken(newRefreshToken);
        newRefreshTokenEntity.setUser(refreshTokenEntity.getUser());
        newRefreshTokenEntity.setFingerprint(request.fingerprint());
        newRefreshTokenEntity.setExpiresAt(Instant.now().plus(Duration.ofDays(jwtService.getRefreshDays())));
        refreshTokenRepository.save(newRefreshTokenEntity);

        return new TokenPair(newAccessToken, newRefreshToken);
    }

    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).ifPresent(t -> {
            t.setRevoked(true);
            refreshTokenRepository.save(t);
        });
    }
}
