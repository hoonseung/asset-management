package com.sewon.account.response;

import com.sewon.account.dto.AccountResult;

public record AccountOneResponse(
    Long id,
    String username,
    String name,
    Long affiliationId,
    String role
) {

    public static AccountOneResponse from(AccountResult account) {
        return new AccountOneResponse(
            account.id(),
            account.username(),
            account.name(),
            account.affiliationId(),
            account.role()
        );
    }
}
