package com.sewon.dsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sewon.asset.constant.AssetDivision;
import com.sewon.asset.constant.AssetStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AssetQueryResponseDto {

    private Long id;
    private String barcode;
    private String corporationName;
    private String department;
    private String location;
    private AssetDivision division;
    private Long assetTypeId;
    private String parentCategory;
    private String childCategory;
    private AssetStatus status;
    private String manufacturer;
    private String model;
    private LocalDateTime acquisitionDate;
    private Integer acquisitionPrice;
    private String registerName;

    @QueryProjection
    public AssetQueryResponseDto(Long id, String barcode, String corporationName, String department,
        String location, AssetDivision division, Long assetTypeId, String parentCategory,
        String childCategory,
        AssetStatus status,
        String manufacturer, String model, LocalDateTime acquisitionDate, Integer acquisitionPrice,
        String registerName) {
        this.id = id;
        this.barcode = barcode;
        this.corporationName = corporationName;
        this.department = department;
        this.location = location;
        this.division = division;
        this.assetTypeId = assetTypeId;
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.status = status;
        this.manufacturer = manufacturer;
        this.model = model;
        this.acquisitionDate = acquisitionDate;
        this.acquisitionPrice = acquisitionPrice;
        this.registerName = registerName;
    }
}
