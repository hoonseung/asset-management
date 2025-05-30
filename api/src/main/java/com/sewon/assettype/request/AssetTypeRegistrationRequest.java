package com.sewon.assettype.request;

import com.sewon.assettype.model.AssetType;
import com.sewon.common.constant.NationType;

public record AssetTypeRegistrationRequest(
    Long parentId,
    String name,
    String nationType
) {

    public AssetType toAssetType() {
        return AssetType.of(name, NationType.valueOf(nationType.toUpperCase()));
    }
}
