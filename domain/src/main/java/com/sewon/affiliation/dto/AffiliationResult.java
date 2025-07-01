package com.sewon.affiliation.dto;

import com.sewon.affiliation.model.Affiliation;
import com.sewon.assetlocation.dto.AssetLocationResult;
import java.util.List;

public record AffiliationResult(
    Long id,
    String corporation,
    String department,
    List<AssetLocationResult> assetLocations
) {


    public static AffiliationResult from(Affiliation affiliation) {
        return new AffiliationResult(
            affiliation.getId(),
            affiliation.getCorporation().getName(),
            affiliation.getDepartment(),
            affiliation.getAssetLocations().stream()
                .map(AssetLocationResult::from)
                .toList()
        );
    }

    public String getCorporationName() {
        return corporation;
    }
}
