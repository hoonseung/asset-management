package com.sewon.asset.request;

import com.sewon.asset.dto.AssetProperties;
import com.sewon.asset.dto.GeneralAssetProperties;
import java.time.LocalDateTime;

public record AssetRegistrationRequest(
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
    Integer acquisitionPrice
) {

    public AssetProperties toGeneralAssetProperties() {
        return GeneralAssetProperties.of(
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
            acquisitionPrice
        );
    }
}
