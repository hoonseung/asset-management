package com.sewon.notification.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageRoutingKey {

    RENTAL_REQUEST_RECEIVED_KEY("rental.request"),
    RENTAL_REQUEST_APPROVE_KEY("rental.request.approve"),
    RENTAL_REQUEST_REJECT_KEY("rental.request.reject"),

    RETURN_REQUEST_RECEIVED_KEY("rental.request.return"),
    RETURN_REQUEST_APPROVE_KEY("rental.request.return.approve"),
    RETURN_REQUEST_REJECT_KEY("rental.request.return.reject"),

    RENTAL_CHECK_EXPIRATION_KEY("rental.expire.asset"),

    STOCK_TAKING_LOCATION_EXPIRATION_KEY("stock.taking.expire.location"),

    READ_CHECK_KEY("notify.read.check");

    private final String key;

}
