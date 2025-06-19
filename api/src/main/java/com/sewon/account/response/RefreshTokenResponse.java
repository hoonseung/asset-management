package com.sewon.account.response;

import com.sewon.security.model.auth.AccessToken;
import com.sewon.security.model.auth.RefreshToken;

public record RefreshTokenResponse(
    AccessToken accessToken,
    RefreshToken refreshToken
) {

    public static RefreshTokenResponse from(AccessToken accessToken,
        RefreshToken refreshToken) {
        return new RefreshTokenResponse(
            accessToken,
            refreshToken
        );
    }
}
