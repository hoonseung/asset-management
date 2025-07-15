package com.sewon.notification.constant;

import java.text.MessageFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotifyMessage {

    RENTAL_REQUEST_RECEIVED("자산 대여 요청이 있습니다."),
    RENTAL_REQUEST_APPROVE("자산 대여 요청이 승인되었습니다."),
    RENTAL_REQUEST_REJECT("자산 대여 요청이 거절되었습니다."),

    RETURN_REQUEST_RECEIVED("자산 반납 요청이 있습니다."),
    RETURN_REQUEST_APPROVE("자산 반납 요청이 승인되었습니다."),
    RETURN_REQUEST_REJECT("자산 대여 요청이 거절되었습니다."),

    RENTAL_CHECK_EXPIRATION("{0} 자산 대여 기간이 만료되었습니다."),

    STOCK_TAKING_LOCATION_EXPIRATION("{0} 위치 실사 종료일까지 {1}일 남았습니다.");

    private final String template;


    public String formating(Object... args) {
        return MessageFormat.format(template, args);
    }
}
