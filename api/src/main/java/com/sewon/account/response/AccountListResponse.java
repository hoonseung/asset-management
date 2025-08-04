package com.sewon.account.response;

import com.sewon.account.dto.AccountResult;
import java.util.List;

public record AccountListResponse(
    List<AccountOneResponse> responses
) {

    public static AccountListResponse from(List<AccountResult> accounts) {
        return new AccountListResponse(accounts.stream()
            .map(AccountOneResponse::from)
            .toList());
    }
}
