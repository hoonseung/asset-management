package com.sewon.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InternalErrorCode implements ErrorCode {


    INVALID_METHOD_VALUE("INTERNAL_001", "매개변수가 유효하지 않습니다."),
    CONSTRAINT_VIOLATION("INTERNAL_002", "제약조건 위반입니다."),
    INTERNAL_ERROR("INTERNAL_003", "내부 오류입니다."),

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
