package com.sewon.assettype.response;

import com.sewon.assettype.model.AssetType;

public record AssetTypeOneChildResponse(

    Long childId,
    String name
) {


    public static AssetTypeOneChildResponse from(AssetType assetType) {
        return new AssetTypeOneChildResponse(
            assetType.getId(),
            assetType.getName()
        );
    }
}

