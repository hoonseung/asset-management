package com.sewon.stocktaking.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetCheckingStatus {

    MATCH("확인일치", 1),
    MISMATCH("확인불일치", 0),

    ;

    private final String description;
    private final int value;

    public static AssetCheckingStatus fromValue(int value) {
        for (AssetCheckingStatus status : AssetCheckingStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException(value + "is Unknown value");
    }
}
