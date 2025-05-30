package com.sewon.corporation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.corporation.model.Corporation;
import java.util.List;

public record CorporationListResponse(

    @JsonProperty("corporationList")
    List<CorporationOneResponse> corporationOneResponses
) {

    public static CorporationListResponse from(List<Corporation> corporation) {
        return new CorporationListResponse(
            corporation.stream()
                .map(CorporationOneResponse::from)
                .toList()
        );
    }
}
