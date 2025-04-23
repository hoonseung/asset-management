package com.sewon.outbound.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OutboundType {

    TRANSFER("이관", 0),
    RENTAL("대여", 1),
    DISPOSE("폐기", 2),

    ;

    private final String description;
    private final int value;


    public static OutboundType fromValue(int value) {
        for (OutboundType type : OutboundType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException(value + "is Unknown value");
    }
}
