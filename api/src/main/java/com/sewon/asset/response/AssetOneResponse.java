package com.sewon.asset.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sewon.asset.dto.result.AllAssetResult;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(Include.NON_EMPTY)
@Getter
@AllArgsConstructor
public class AssetOneResponse {

    private final Long id;
    private final String barcode;
    private final String corporation;
    private final String department;
    private final String location;
    private final String division;
    private final String parentCategory;
    private final String childCategory;
    private final String status;
    private final String manufacturer;
    private final String model;
    private final LocalDate acquisitionDate;
    private final Integer acquisitionPrice;
    private final String registerName;
    private final String cpu;
    private final Integer ram;
    private BigDecimal storage;
    private String gpu;
    private final LocalDate registrationDate;


    public static AssetOneResponse from(AllAssetResult asset) {
        return new AssetOneResponse(
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
            asset.getGpu(),
            asset.getRegistrationDate()
        );
    }
}
