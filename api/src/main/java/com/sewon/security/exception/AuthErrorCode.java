package com.sewon.security.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    UNAUTHENTICATED_ACCESS("AUTH_001", "만료된 인증이거나 올바르지 않은 인증 접근입니다."),
    UNAUTHORIZED_ACCESS("AUTH_002", "권한이 없는 접근입니다."),
    INVALID_TOKEN_TYPE("AUTH_003", "유효하지 않은 토큰 타입입니다."),

    INTERNAL_ERROR("INTERNAL_001", "내부 오류입니다."),

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
