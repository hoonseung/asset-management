package com.sewon.rental.request;

import java.time.LocalDate;

public record RentalRequestRequest(
    Long assetId,
    Long locationId,
    LocalDate fromDate,
    LocalDate toDate
) {

}
