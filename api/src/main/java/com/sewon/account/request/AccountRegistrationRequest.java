package com.sewon.account.request;

import com.sewon.account.model.Account;

public record AccountRegistrationRequest(
    String username,
    String password,
    String name,
    String corporation,
    String department,
    Integer role
) {

    public Account toAccount() {
        if (role == 0) {
            return Account.createAdmin(username, password, name);
        }
        return Account.createGeneral(username, password, name);
    }

}
