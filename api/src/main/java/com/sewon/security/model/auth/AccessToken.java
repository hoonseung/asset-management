package com.sewon.security.model.auth;

import java.time.Duration;

public record AccessToken(
    String token,
    Duration expiration
) {

    public static AccessToken of(String token, Duration expiration) {
        return new AccessToken(token, expiration);
    }
}
