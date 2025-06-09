package com.sewon.rental.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RentalErrorCode implements ErrorCode {

    RENTAL_NOT_FOUND("RENTAL_01", "자산대여 기록을 찾을 수 없습니다."),

    ;

    private final String code;
    private final String description;


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return description;
    }
}
