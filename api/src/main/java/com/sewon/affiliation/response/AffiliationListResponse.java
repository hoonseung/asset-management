package com.sewon.affiliation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.affiliation.model.Affiliation;
import java.util.List;

public record AffiliationListResponse(

    @JsonProperty("list")
    List<AffiliationOneResponse> responses
) {

    public static AffiliationListResponse from(List<Affiliation> affiliation) {
        return new AffiliationListResponse(
            affiliation.stream()
                .map(AffiliationOneResponse::from)
                .toList()
        );
    }

}
