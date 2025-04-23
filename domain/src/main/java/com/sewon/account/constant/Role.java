package com.sewon.account.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("관리자", 0),
    GENERAL("일반사용자", 1);

    private final String description;
    private final int value;


    public static Role fromValue(int value) {
        for (Role role : Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException(value + "is Unknown value");
    }
}
