package com.sewon.security.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.sewon.security.application.JwtBlackListService;
import com.sewon.security.config.JwtTokenProperties;
import com.sewon.security.model.auth.AccessToken;
import com.sewon.security.model.auth.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenHandler {

    private final JwtTokenProperties properties;
    private final SecretKey key;

    private final JwtBlackListService jwtBlackListService;

    private static final String ACCESS_TYPE = "access";
    private static final String REFRESH_TYPE = "refresh";

    public JwtTokenHandler(JwtTokenProperties properties, JwtBlackListService jwtBlackListService) {
        this.properties = properties;
        this.jwtBlackListService = jwtBlackListService;
        this.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes(UTF_8));
    }

    public AccessToken generateAccessToken(Long id, String username, String role) {
        return AccessToken.of(
            getToken(id, username, properties.getAccessTokenExpiration(), ACCESS_TYPE, role),
            Duration.of(properties.getAccessTokenExpiration(), ChronoUnit.MILLIS)
        );
    }

    public RefreshToken generateRefreshToken(Long id, String username, String role) {
        return RefreshToken.of(
            getToken(id, username, properties.getRefreshTokenExpiration(), REFRESH_TYPE, role),
            Duration.of(properties.getRefreshTokenExpiration(), ChronoUnit.MILLIS)
        );
    }

    public String getUsername(String token) {
        return getClaims(token)
            .getSubject();
    }

    public Long getId(String token) {
        return getClaims(token)
            .get("id", Long.class);
    }

    public List<String> getRoles(String token) {
        return List.of(getClaims(token).get("role", String.class));
    }

    public String getType(String token) {
        return getClaims(token)
            .get("type", String.class);
    }

    public AccessToken getAccessToken(String token) {
        return AccessToken.of(token,
            Duration.between(LocalDateTime.now(), getExpiration(token)));
    }

    public boolean isValidToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isValidAccessToken(String token) {
        if (isValidToken(token) && isNotExpired(token) && isAccessToken(token)) {
            return !jwtBlackListService.isBlacklistToken(
                token,
                getId(token),
                getType(token));
        }
        return false;
    }

    public boolean isValidRefreshToken(String token) {
        return isRefreshToken(token) && isValidToken(token) && isNotExpired(token);
    }

    public boolean isAccessToken(String token) {
        return getType(token).equals(ACCESS_TYPE);
    }

    public boolean isRefreshToken(String token) {
        return getType(token).equals(REFRESH_TYPE);
    }

    public boolean isNotExpired(String token) {
        return getClaims(token)
            .getExpiration()
            .after(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public LocalDateTime getExpiration(String token) {
        return getClaims(token)
            .getExpiration().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private String getToken(Long id, String username, Long time, String type, String role) {
        return Jwts.builder()
            .subject(username)
            .claim("id", id)
            .claim("role", role)
            .claim("type", type)
            .issuedAt(new Date())
            .expiration(
                new Date(System.currentTimeMillis() + time))
            .signWith(key)
            .compact();
    }
}
