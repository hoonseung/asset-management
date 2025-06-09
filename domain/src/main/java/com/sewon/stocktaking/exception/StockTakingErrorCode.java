package com.sewon.stocktaking.exception;

import com.sewon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StockTakingErrorCode implements ErrorCode {

    STOCK_TAKING_ALREADY_LOCATION("ASSET_RENTAL_01", "해당일에 이미 진행한 실사 위치입니다."),

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
