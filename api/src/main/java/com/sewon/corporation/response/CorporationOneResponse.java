package com.sewon.corporation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.affiliation.response.AffiliationOneResponse;
import com.sewon.corporation.model.Corporation;
import java.util.List;

public record CorporationOneResponse(

    Long id,
    String name,
    String nationType,
    @JsonProperty("affiliationList")
    List<AffiliationOneResponse> affiliationOneResponses
) {

    public static CorporationOneResponse from(Corporation corporation) {
        return new CorporationOneResponse(
            corporation.getId(),
            corporation.getName(),
            corporation.getNationType(),
            corporation.getAffiliations().stream()
                .map(AffiliationOneResponse::from)
                .toList()
        );
    }
}
