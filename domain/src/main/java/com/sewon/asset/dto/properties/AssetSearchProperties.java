package com.sewon.asset.dto.properties;

import java.time.LocalDate;

public record AssetSearchProperties(
    Long corporationId,
    Long affiliationId,
    Long locationId,
    Long parentTypeId,
    Long childTypeId,
    LocalDate after,
    LocalDate before,
    int size
) {

    public static AssetSearchProperties of(
        Long corporationId,
        Long affiliationId,
        Long locationId,
        Long pTypeId,
        Long cTypeId,
        LocalDate after,
        LocalDate before,
        int size
    ) {
        return new AssetSearchProperties(
            corporationId,
            affiliationId,
            locationId,
            pTypeId,
            cTypeId,
            after,
            before,
            size
        );
    }

    public AssetSearchProperties toWithOutBetween() {
        return new AssetSearchProperties(
            this.corporationId,
            this.affiliationId,
            this.locationId,
            this.parentTypeId,
            this.childTypeId,
            null,
            null,
            this.size
        );
    }
}
