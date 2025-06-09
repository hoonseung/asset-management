package com.sewon.assettype.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AssetTypeErrorCode implements ErrorCode {

    ASSET_TYPE_NOT_FOUND("ASSET_TYPE_01", "자산분류나 품목을 찾을 수 없습니다."),

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
