package com.sewon.assetlocation.request;

import jakarta.validation.constraints.NotNull;

public record AssetLocationRegistrationRequest(
    @NotNull
    Long affiliationId,
    String location
) {

}
