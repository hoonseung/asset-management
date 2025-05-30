package com.sewon.asset.request.electronic;

import com.sewon.asset.dto.AssetProperties;
import com.sewon.asset.dto.ElectronicAssetProperties;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ElectronicAssetRegistrationRequest(
    String corporation,
    String department,
    String location,
    Integer division,
    String parentType,
    String childType,
    Integer status,
    String manufacturer,
    String model,
    LocalDateTime acquisitionDate,
    Integer acquisitionPrice,
    String cpu,
    Integer ram,
    BigDecimal storage,
    String gpu
) {


    public AssetProperties toElectronicAssetProperties() {
        return ElectronicAssetProperties.of(
            corporation,
            department,
            location,
            division,
            parentType,
            childType,
            status,
            manufacturer,
            model,
            acquisitionDate,
            acquisitionPrice,
            cpu,
            ram,
            storage,
            gpu
        );
    }
}
