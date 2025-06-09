package com.sewon.corporation.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CorporationErrorCode implements ErrorCode {

    CORPORATION_NOT_FOUND("CORPORATION_01", "법인을 찾을 수 없습니다."),

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
