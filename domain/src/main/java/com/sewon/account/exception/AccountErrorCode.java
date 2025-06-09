package com.sewon.account.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountErrorCode implements ErrorCode {

    USER_NOT_FOUND("USER_001", "사용자를 찾을 수 없습니다."),
    USER_INVALID("USER_002", "올바르지 않은 사용자 입니다."),
    USER_PW_INVALID("USER_003", "패스워드가 올바르지 않습니다"),
    USER_DUPLICATED("USER_004", "중복된 아이디입니다."),

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
