package com.sewon.assettype.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AssetTypeErrorCode implements ErrorCode {

    ASSET_TYPE_NOT_FOUND("ASSET_TYPE_01", "자산분류나 품목을 찾을 수 없습니다."),
    ASSET_TYPE_DUPLICATION("ASSET_TYPE_02", "자산분류나 품목이 이미 존재합니다."),
    ASSET_TYPE_DELETE_ERROR("ASSET_TYPE_03", "자산분류를 삭제할 수 없습니다."),

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
