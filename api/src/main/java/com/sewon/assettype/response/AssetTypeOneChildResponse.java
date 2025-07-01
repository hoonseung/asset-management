package com.sewon.assettype.response;

import com.sewon.assettype.dto.AssetTypeChildResult;

public record AssetTypeOneChildResponse(

    Long childId,
    String name
) {


    public static AssetTypeOneChildResponse from(AssetTypeChildResult assetType) {
        return new AssetTypeOneChildResponse(
            assetType.childId(),
            assetType.name()
        );
    }
}

