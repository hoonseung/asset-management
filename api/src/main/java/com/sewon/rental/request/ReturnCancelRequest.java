package com.sewon.rental.request;

import java.util.List;

public record ReturnCancelRequest(
    List<Long> ids
) {

}
