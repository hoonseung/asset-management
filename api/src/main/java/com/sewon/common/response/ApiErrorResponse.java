package com.sewon.common.response;

import com.sewon.common.exception.ErrorCode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ApiErrorResponse(

    String time,
    Integer status,
    String code,
    String message
) {

    public static ApiErrorResponse of(Integer status, ErrorCode errorCode) {
        return new ApiErrorResponse(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            status,
            errorCode.getCode(),
            errorCode.getMessage()
        );
    }
}
