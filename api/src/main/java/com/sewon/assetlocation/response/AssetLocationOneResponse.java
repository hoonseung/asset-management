package com.sewon.assetlocation.response;

import com.sewon.assetlocation.dto.AssetLocationResult;

public record AssetLocationOneResponse(
    Long locationId,
    String location
) {

    public static AssetLocationOneResponse from(AssetLocationResult assetLocation) {
        return new AssetLocationOneResponse(assetLocation.id(), assetLocation.location());
    }
}
