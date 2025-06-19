package com.sewon.security.model.auth;

import java.time.Duration;

public record RefreshToken(
    String token,
    Duration expiration
) {

    public static RefreshToken of(String token, Duration expiration) {
        return new RefreshToken(token, expiration);
    }

}
