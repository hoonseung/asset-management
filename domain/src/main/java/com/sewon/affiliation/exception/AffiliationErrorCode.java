package com.sewon.affiliation.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AffiliationErrorCode implements ErrorCode {

    AFFILIATION_NOT_FOUND("AFFILIATION_001", "소속을 찾을 수 없습니다."),

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
