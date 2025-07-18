package com.sewon.asset.dto.result;

import com.sewon.asset.model.Asset;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssetResult {

    private final Long id;
    private final String barcode;
    private final String corporation;
    private final String department;
    private final String location;
    private final String division;
    private final Long assetTypeId;
    private final String parentCategory;
    private final String childCategory;
    private final String status;
    private final String manufacturer;
    private final String model;
    private final LocalDate acquisitionDate;
    private final Integer acquisitionPrice;
    private final String registerName;


    public static AssetResult from(Asset asset) {
        return new AssetResult(
            asset.getId(),
            asset.getBarcodeValue(),
            asset.getCorporationName(),
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
