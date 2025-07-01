package com.sewon.affiliation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.affiliation.dto.AffiliationResult;
import java.util.List;

public record AffiliationListResponse(

    @JsonProperty("list")
    List<AffiliationOneResponse> responses
) {

    public static AffiliationListResponse from(List<AffiliationResult> affiliations) {
        return new AffiliationListResponse(
            affiliations.stream()
                .map(AffiliationOneResponse::from)
                .toList()
        );
    }

}
