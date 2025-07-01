package com.sewon.asset.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetDivision {

    PURCHASE("구매자산", 0),
    RENTAL("이관자산", 1);


    private final String description;
    private final int value;


    public static AssetDivision fromValue(int value) {
        for (AssetDivision division : AssetDivision.values()) {
            if (division.value == value) {
                return division;
            }
        }
        throw new IllegalArgumentException(value + "is Unknown value");
    }
}
