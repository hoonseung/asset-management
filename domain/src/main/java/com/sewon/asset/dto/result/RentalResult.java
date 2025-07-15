package com.sewon.asset.dto.result;

import com.sewon.asset.model.Asset;

public record RentalResult(Long id, String barcode, String location, String division, String status,
                           String registerName) {

    public static RentalResult from(Asset asset) {
        return new RentalResult(
            asset.getId(),
            asset.getBarcodeValue(),
            asset.getLocation(),
            asset.getDivision(),
            asset.getStatus(),
            asset.getAccountName()
        );
    }
}
