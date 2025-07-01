package com.sewon.corporation.dto;

import com.sewon.affiliation.dto.AffiliationResult;
import com.sewon.corporation.model.Corporation;
import java.util.List;

public record CorporationResult(
    Long id,
    String name,
    String nationType,
    List<AffiliationResult> affiliationResults
) {

    public static CorporationResult from(Corporation corporation) {
        return new CorporationResult(
            corporation.getId(),
            corporation.getName(),
            corporation.getNationType(),
            corporation.getAffiliations().stream()
                .map(AffiliationResult::from)
                .toList()
        );
    }

}
