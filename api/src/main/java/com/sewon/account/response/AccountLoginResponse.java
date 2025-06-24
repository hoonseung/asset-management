package com.sewon.account.response;

import com.sewon.account.model.Account;
import com.sewon.security.model.auth.AccessToken;
import com.sewon.security.model.auth.RefreshToken;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record AccountLoginResponse(
    Long id,
    Long affiliationId,
    String username,
    String name,
    String corporation,
    String department,
    AccessToken accessToken,
    RefreshToken refreshToken,
    String loginDate
) {

    public static AccountLoginResponse from(Account account, AccessToken accessToken,
        RefreshToken refreshToken) {
        return new AccountLoginResponse(
            account.getId(),
            account.getAffiliation().getId(),
            account.getUsername(),
            account.getName(),
            account.getAffiliation().getCorporation(),
            account.getAffiliation().getDepartment(),
            accessToken,
            refreshToken,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}
