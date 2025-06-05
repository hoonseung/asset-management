package com.sewon.rental.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RentalStatus {

    REQUEST("요청중", 0),
    RENT("대여중", 1),
    EXPIRE("만료", 2),

    ;

    private final String description;
    private final int value;


    public static RentalStatus fromValue(int value) {
        for (RentalStatus status : RentalStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException(value + "is Unknown value");
    }
}
