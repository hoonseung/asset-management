package com.sewon.stocktaking.request;

import java.time.LocalDate;
import java.util.List;

public record StockTakingRegistrationRequest(
    List<String> barcodes,
    LocalDate auditingDate,
    String realLocation
) {

}
