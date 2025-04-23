package com.sewon.asset.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetStatus {

    USE("사용", 0),
    UNUSED("미사용", 1),
    RENTAL("대여", 2),

    ;

    private final String description;
    private final int value;


    public static AssetStatus fromValue(int value) {
        for (AssetStatus status : AssetStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException(value + "is Unknown value");
    }
}
