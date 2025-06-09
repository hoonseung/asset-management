package com.sewon.asset.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AssetErrorCode implements ErrorCode {

    ASSET_NOT_FOUND("ASSET_001", "자산을 찾을 수 없습니다."),

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
