package com.sewon.inbound.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InboundType {

    REGISTRATION("등록", 0),
    RENTAL("대여", 1),
    TRANSFER("이관", 2);

    private final String description;
    private final int value;


    public static InboundType fromValue(int value) {
        for (InboundType type : InboundType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException(value + "is Unknown value");
    }

    public static InboundType getByAssetStatus(int value) {
        switch (value) {
            case 0, 1 -> {
                return InboundType.REGISTRATION;
            }
            case 2 -> {
                return InboundType.RENTAL;
            }
            default -> {
                return null;
            }
        }
    }
}
