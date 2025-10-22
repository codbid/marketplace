package org.example.marketplace.app.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    @Value("${security.secret}")
    private String secret;

    @Value("${security.access-minutes}")
    private long accessMinutes;

    @Getter
    @Value("${security.refresh-days}")
    private long refreshDays;

    private Key key() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(UserPrincipal user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(Duration.ofMinutes(accessMinutes))))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserPrincipal user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("typ", "refresh")
                .issuedAt(Date.from((now)))
                .expiration(Date.from(now.plus(Duration.ofDays(refreshDays))))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser().setSigningKey(key()).build().parseSignedClaims(token);
    }

    public String extractUsername(String token) {
        return parse(token).getBody().getSubject();
    }

    public boolean validate(String token, UserDetails user) {
        var claims = parse(token).getBody();
        return user.getUsername().equals(claims.getSubject()) && claims.getExpiration().after(new Date()) && user.isEnabled();
    }

    public boolean isRefreshToken(String token) {
        Object type = parse(token).getBody().get("typ");
        return "refresh".equals(type);
    }

}
