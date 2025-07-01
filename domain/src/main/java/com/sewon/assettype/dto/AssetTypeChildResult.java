package com.sewon.assettype.dto;

import com.sewon.assettype.model.AssetType;

public record AssetTypeChildResult(
    Long childId,
    String name
) {

    public static AssetTypeChildResult from(AssetType assetType) {
        return new AssetTypeChildResult(
            assetType.getId(),
            assetType.getName()
        );
    }
}
