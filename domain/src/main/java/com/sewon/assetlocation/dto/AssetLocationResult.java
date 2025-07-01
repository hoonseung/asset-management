package com.sewon.assetlocation.dto;

import com.sewon.assetlocation.model.AssetLocation;

public record AssetLocationResult(
    Long id,
    String location
) {

    public static AssetLocationResult from(AssetLocation assetLocation) {
        return new AssetLocationResult(
            assetLocation.getId(),
            assetLocation.getLocation()
        );
    }
}
