package com.sewon.asset.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sewon.asset.model.Asset;
import java.time.LocalDate;

@JsonInclude(Include.NON_EMPTY)
public record AssetOneResponse(

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

    public static AssetOneResponse from(Asset asset) {
        return new AssetOneResponse(
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
}
