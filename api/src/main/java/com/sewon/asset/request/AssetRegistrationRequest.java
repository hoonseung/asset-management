package com.sewon.asset.request;

import com.sewon.asset.dto.AssetProperties;
import com.sewon.asset.dto.GeneralAssetProperties;
import java.time.LocalDateTime;

public record AssetRegistrationRequest(
    Long locationId,
    Integer division,
    Long parentTypeId,
    Long childTypeId,
    Integer status,
    String manufacturer,
    String model,
    LocalDateTime acquisitionDate,
    Integer acquisitionPrice
) {

    public AssetProperties toGeneralAssetProperties() {
        return GeneralAssetProperties.of(
            locationId,
            division,
            parentTypeId,
            childTypeId,
            status,
            manufacturer,
            model,
            acquisitionDate,
            acquisitionPrice
        );
    }
}
