package com.sewon.corporation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.affiliation.response.AffiliationOneResponse;
import com.sewon.corporation.dto.CorporationResult;
import java.util.List;

public record CorporationOneResponse(

    Long corporationId,
    String name,
    String nationType,
    @JsonProperty("affiliationList")
    List<AffiliationOneResponse> affiliationOneResponses
) {

    public static CorporationOneResponse from(CorporationResult corporation) {
        return new CorporationOneResponse(
            corporation.id(),
            corporation.name(),
            corporation.nationType(),
            corporation.affiliationResults().stream()
                .map(AffiliationOneResponse::from)
                .toList()
        );
    }
}
