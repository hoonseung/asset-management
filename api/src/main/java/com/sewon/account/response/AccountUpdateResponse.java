package com.sewon.account.response;

import com.sewon.account.dto.AccountResult;

public record AccountUpdateResponse(
    Long id,
    Long affiliationId,
    String username,
    String name,
    String corporation,
    String department
) {

    public static AccountUpdateResponse from(AccountResult account) {
        return new AccountUpdateResponse(
            account.id(),
            account.affiliationId(),
            account.username(),
            account.name(),
            account.corporation(),
            account.department()
        );
    }
}
