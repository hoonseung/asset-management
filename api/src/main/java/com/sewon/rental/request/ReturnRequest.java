package com.sewon.rental.request;

import java.util.List;

public record ReturnRequest(
    List<Long> ids
) {

}
