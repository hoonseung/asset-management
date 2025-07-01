package com.sewon.affiliation.response;

import com.sewon.affiliation.dto.AffiliationResult;
import com.sewon.assetlocation.response.AssetLocationOneResponse;
import java.util.List;

public record AffiliationOneResponse(

    Long affiliationId,
    String corporation,
    String department,
    List<AssetLocationOneResponse> locations
) {

    public static AffiliationOneResponse from(AffiliationResult affiliation) {
        return new AffiliationOneResponse(
            affiliation.id(),
            affiliation.getCorporationName(),
            affiliation.department(),
            affiliation.assetLocations().stream()
                .map(AssetLocationOneResponse::from)
                .toList()
        );
    }

}
