package com.sewon.rental.dto;

import com.sewon.rental.model.AssetRental;
import java.time.LocalDate;

public record AssetRentalResult(
    Long id,
    Long assetId,
    String barcode,
    String corporation,
    String department,
    String location,
    String rentLocation,
    String parentType,
    String childType,
    String renter,
    String register,
    Integer status,
    LocalDate fromDate,
    LocalDate toDate
) {

    public static AssetRentalResult from(AssetRental rental) {
        return new AssetRentalResult(
            rental.getId(),
            rental.getAsset().getId(),
            rental.getBarcodeValue(),
            rental.getCorporation(),
            rental.getDepartment(),
            rental.getAssetLocation(),
            rental.getRentLocation(),
            rental.getParentCategory(),
            rental.getChildCategory(),
            rental.getRenter(),
            rental.getRegister(),
            rental.getRentalStatus().getValue(),
            rental.getFromDate(),
            rental.getToDate()
        );
    }
}
