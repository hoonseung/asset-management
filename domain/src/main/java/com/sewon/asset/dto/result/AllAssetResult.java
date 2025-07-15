package com.sewon.asset.dto.result;

import com.sewon.asset.constant.AssetDivision;
import com.sewon.asset.constant.AssetStatus;
import com.sewon.assettype.model.AssetType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AllAssetResult {

    private final Long id;
    private final String barcode;
    private final String corporation;
    private final String department;
    private final String location;
    private final String division;
    private final AssetType assetType;
    private final Long assetTypeId;
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
    private final BigDecimal storage;
    private final String gpu;
    private final LocalDate registrationDate;


    public AllAssetResult(Long id, String barcode, String corporation, String department,
        String location, AssetDivision division, AssetType assetType, Long assetTypeId,
        String parentCategory,
        String childCategory,
        AssetStatus status, String manufacturer, String model, LocalDateTime acquisitionDate,
        Integer acquisitionPrice, String registerName, String cpu, Integer ram, BigDecimal storage,
        String gpu, LocalDateTime registrationDate) {
        this.id = id;
        this.barcode = barcode;
        this.corporation = corporation;
        this.department = department;
        this.location = location;
        this.division = division.getDescription();
        this.assetType = assetType;
        this.assetTypeId = assetTypeId;
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.status = status.getDescription();
        this.manufacturer = manufacturer;
        this.model = model;
        this.acquisitionDate = acquisitionDate.toLocalDate();
        this.acquisitionPrice = acquisitionPrice;
        this.registerName = registerName;
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.gpu = gpu;
        this.registrationDate = registrationDate.toLocalDate();
    }
}
