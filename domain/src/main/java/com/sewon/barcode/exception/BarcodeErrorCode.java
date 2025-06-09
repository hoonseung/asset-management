package com.sewon.barcode.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BarcodeErrorCode implements ErrorCode {

    BARCODE_NOT_FOUND("BARCODE_01", "바코드를 찾을 수 없습니다."),
    ASSET_BARCODE_NOT_FOUND("ASSET_002", "바코드로 자산을 찾을 수 없습니다."),

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
