package com.sewon.assetlocation.response;

import com.sewon.assetlocation.model.AssetLocation;

public record AssetLocationOneResponse(
    Long locationId,
    String location
) {

    public static AssetLocationOneResponse from(AssetLocation assetLocation) {
        return new AssetLocationOneResponse(assetLocation.getId(), assetLocation.getLocation());
    }
}
