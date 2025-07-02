package com.sewon.stocktaking.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StockTakingErrorCode implements ErrorCode {

    STOCK_TAKING_ALREADY_LOCATION("ASSET_STOCK_TAKING_01", "해당일에 이미 실사 완료된 위치입니다."),
    STOCK_TAKING_EXPIRATION("ASSET_STOCK_TAKING_02", "시작일로부터 만료된 실사입니다."),
    STOCK_TAKING_NOT_FOUND("ASSET_STOCK_TAKING_03", "실사기록을 찾을 수 없습니다.");

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
