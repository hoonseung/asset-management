package com.sewon.asset.dto;

import java.time.LocalDate;

public record AssetSearchProperties(
    Long locationId,
    Long parentTypeId,
    Long childTypeId,
    LocalDate after,
    LocalDate before,
    int size
) {

    public static AssetSearchProperties of(
        Long locationId,
        Long pTypeId,
        Long cTypeId,
        LocalDate after,
        LocalDate before,
        int size
    ) {
        return new AssetSearchProperties(
            locationId,
            pTypeId,
            cTypeId,
            after,
            before,
            size
        );
    }
}
