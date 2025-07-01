package com.sewon.asset.dto;

import com.sewon.asset.model.Asset;
import java.time.LocalDate;

public record AssetResult(
    Long id,
    String barcode,
    String corporation,
    String department,
    String location,
    String division,
    Long assetTypeId,
    String parentCategory,
    String childCategory,
    String status,
    String manufacturer,
    String model,
    LocalDate acquisitionDate,
    Integer acquisitionPrice,
    String registerName

) {

    public static AssetResult from(Asset asset) {
        return new AssetResult(
            asset.getId(),
            asset.getBarcodeValue(),
            asset.getCorporation(),
            asset.getDepartment(),
            asset.getLocation(),
            asset.getDivision(),
            asset.getAssetTypeId(),
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

    public static AssetResult of(
        Long id,
        String barcode,
        String corporation,
        String department,
        String location,
        String division,
        Long assetTypeId,
        String parentCategory,
        String childCategory,
        String status,
        String manufacturer,
        String model,
        LocalDate acquisitionDate,
        Integer acquisitionPrice,
        String registerName
    ) {
        return new AssetResult(
            id, barcode, corporation, department, location, division, assetTypeId,
            parentCategory, childCategory,
            status, manufacturer, model, acquisitionDate, acquisitionPrice,
            registerName
        );
    }
}
