package com.sewon.account.dto;

import com.sewon.account.model.Account;

public record AccountResult(
    Long id,
    String username,
    String name,
    String role,
    Long affiliationId,
    String corporation,
    String department
) {

    public static AccountResult from(Account account) {
        return new AccountResult(
            account.getId(),
            account.getUsername(),
            account.getName(),
            account.getRole().name(),
            account.getAffiliation().getId(),
            account.getAffiliation().getCorporation().getName(),
            account.getAffiliation().getDepartment()
        );
    }
}
