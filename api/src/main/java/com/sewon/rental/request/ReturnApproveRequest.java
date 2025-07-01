package com.sewon.rental.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ReturnApproveRequest(
    @NotNull
    List<Long> ids
) {

}
