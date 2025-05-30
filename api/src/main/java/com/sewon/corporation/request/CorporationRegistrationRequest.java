package com.sewon.corporation.request;

import com.sewon.common.constant.NationType;
import com.sewon.corporation.model.Corporation;

public record CorporationRegistrationRequest(
    String name,
    String nationType
) {

    public Corporation toCorporation() {
        return Corporation.of(name, NationType.valueOf(nationType.toUpperCase()));
    }
}
