package com.sewon.stocktaking.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetStockTakingStatus {

    NORMAL("정상", 0),
    ABNORMAL("비정상", 1),
    REPAIR("수리", 2),
    DISPOSE("폐기", 3),
    LOST("분실", 4),

    ;

    private final String description;
    private final int value;

    public static AssetStockTakingStatus fromValue(int value) {
        for (AssetStockTakingStatus status : AssetStockTakingStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException(value + "is Unknown value");
    }
}
