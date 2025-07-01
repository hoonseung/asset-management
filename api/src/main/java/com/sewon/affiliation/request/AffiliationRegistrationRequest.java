package com.sewon.affiliation.request;

import jakarta.validation.constraints.NotNull;

public record AffiliationRegistrationRequest(
    @NotNull
    Long corporationId,
    String department
) {

}
