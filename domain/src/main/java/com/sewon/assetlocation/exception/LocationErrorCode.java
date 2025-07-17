package com.sewon.assetlocation.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LocationErrorCode implements ErrorCode {

    LOCATION_NOT_FOUND("LOCATION_01", "위치를 찾을 수 없습니다."),
    LOCATION_DUPLICATION("LOCATION_02", "위치가 중복됩니다."),
    LOCATION_DELETE_ERROR("AFFILIATION_03", "해당 위치를 삭제할 수 없습니다."),

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
