package com.sewon.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NationType {

    KR(0),
    CN(1),
    VN(2),

    ;

    private final int value;


    public static NationType fromValue(int value) {
        for (NationType nationType : NationType.values()) {
            if (nationType.value == value) {
                return nationType;
            }
        }
        throw new IllegalArgumentException(value + "is Unknown value");
    }
}
