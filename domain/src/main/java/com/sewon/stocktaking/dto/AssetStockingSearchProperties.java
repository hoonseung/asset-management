package com.sewon.stocktaking.dto;

import java.time.LocalDate;

public record AssetStockingSearchProperties(
    Long locationId,
    Long parentTypeId,
    Long childTypeId,
    LocalDate after,
    LocalDate before,
    int size,
    int status
) {

    public static AssetStockingSearchProperties of(
        Long locationId,
        Long pTypeId,
        Long cTypeId,
        LocalDate after,
        LocalDate before,
        int size,
        int status
    ) {
        return new AssetStockingSearchProperties(
            locationId,
            pTypeId,
            cTypeId,
            after,
            before,
            size,
            status
        );
    }
}
