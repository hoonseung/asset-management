package com.sewon.affiliation.response;

import com.sewon.affiliation.model.Affiliation;
import com.sewon.assetlocation.response.AssetLocationOneResponse;
import java.util.List;

public record AffiliationOneResponse(

    Long affiliationId,
    String corporation,
    String department,
    List<AssetLocationOneResponse> locations
) {

    public static AffiliationOneResponse from(Affiliation affiliation) {
        return new AffiliationOneResponse(
            affiliation.getId(),
            affiliation.getCorporation(),
            affiliation.getDepartment(),
            affiliation.getAssetLocations().stream()
                .map(AssetLocationOneResponse::from)
                .toList()
        );
    }

}
