package com.sewon.rental.request;

import java.util.List;

public record RentalDeleteRequest(
    List<Long> ids
) {

}
