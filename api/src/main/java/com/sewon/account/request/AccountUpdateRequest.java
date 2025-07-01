package com.sewon.account.request;

public record AccountUpdateRequest(
    Long affiliationId,
    String name,
    String username,
    String password
) {

}
