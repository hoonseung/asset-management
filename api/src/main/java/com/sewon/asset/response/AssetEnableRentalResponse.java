package com.sewon.asset.response;

import com.sewon.asset.dto.result.RentalResult;


public record AssetEnableRentalResponse(Long id, String barcode, String location, String division,
                                        String status, String registerName) {

    public static AssetEnableRentalResponse from(RentalResult result) {
        return new AssetEnableRentalResponse(
            result.id(),
            result.barcode(),
            result.location(),
            result.division(),
            result.status(),
            result.registerName()
        );
    }

}
