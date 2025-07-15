package com.sewon.stocktaking.dto;

import com.sewon.asset.dto.result.AllAssetResult;
import com.sewon.asset.model.Asset;
import java.time.LocalDate;

public record AssetStockingItemResult(
    Long id,
    String barcode,
    String corporation,
    String department,
    String location,
    String division,
    String parentCategory,
    String childCategory,
    String status,
    String manufacturer,
    String model,
    LocalDate acquisitionDate,
    Integer acquisitionPrice,
    String registerName
) {

    public static AssetStockingItemResult from(Asset asset) {
        return new AssetStockingItemResult(
            asset.getId(),
            asset.getBarcodeValue(),
            asset.getCorporation(),
            asset.getDepartment(),
            asset.getLocation(),
            asset.getDivision(),
            asset.getParentCategory(),
            asset.getChildCategory(),
            asset.getStatus(),
            asset.getManufacturer(),
            asset.getModel(),
            asset.getAcquisitionDate().toLocalDate(),
            asset.getAcquisitionPrice(),
            asset.getAccountName()
        );
    }

    public static AssetStockingItemResult from(AllAssetResult asset) {
        return new AssetStockingItemResult(
            asset.getId(),
            asset.getBarcode(),
            asset.getCorporation(),
            asset.getDepartment(),
            asset.getLocation(),
            asset.getDivision(),
            asset.getParentCategory(),
            asset.getChildCategory(),
            asset.getStatus(),
            asset.getManufacturer(),
            asset.getModel(),
            asset.getAcquisitionDate(),
            asset.getAcquisitionPrice(),
            asset.getRegisterName()
        );
    }
}
