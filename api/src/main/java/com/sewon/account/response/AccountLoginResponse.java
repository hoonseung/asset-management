package com.sewon.account.response;

import com.sewon.account.dto.AccountResult;
import com.sewon.security.model.auth.AccessToken;
import com.sewon.security.model.auth.RefreshToken;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record AccountLoginResponse(
    Long id,
    Long affiliationId,
    String username,
    String name,
    String role,
    String corporation,
    String department,
    AccessToken accessToken,
    RefreshToken refreshToken,
    String loginDate
) {

    public static AccountLoginResponse from(AccountResult account, AccessToken accessToken,
        RefreshToken refreshToken) {
        return new AccountLoginResponse(
            account.id(),
            account.affiliationId(),
            account.username(),
            account.name(),
            account.role(),
            account.corporation(),
            account.department(),
            accessToken,
            refreshToken,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}
