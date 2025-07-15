package com.sewon.asset.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sewon.asset.dto.result.AllAssetResult;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(Include.NON_EMPTY)
public record AllAssetResponse(Long id, String barcode, String corporation, String department,
                               String location, String division, String parentCategory,
                               String childCategory, String status, String manufacturer,
                               String model, LocalDate acquisitionDate, Integer acquisitionPrice,
                               String registerName, String cpu, Integer ram, BigDecimal storage,
                               String gpu) {

    public static AllAssetResponse from(AllAssetResult asset) {
        return new AllAssetResponse(
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
            asset.getRegisterName(),
            asset.getCpu(),
            asset.getRam(),
            asset.getStorage(),
            asset.getGpu()
        );
    }
}
