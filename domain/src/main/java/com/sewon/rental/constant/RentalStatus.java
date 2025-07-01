package com.sewon.rental.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RentalStatus {

    REQUEST_RENTAL("대여요청중", 0),
    RENT("대여중", 1),
    REQUEST_RETURN("반납요청중", 2),
    RETURN("반납완료", 3),
    EXPIRE("만료", 4),

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
