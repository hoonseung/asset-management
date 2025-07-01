package com.sewon.stocktaking.request;

import java.util.List;

public record StockTakingRegistrationRequest(
    List<String> barcodes,
    Long realLocationId
) {

}
