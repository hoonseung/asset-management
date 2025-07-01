package com.sewon.assettype.dto;

import com.sewon.assettype.model.AssetType;
import java.util.List;

public record AssetTypeParentResult(
    Long parentId,
    String parentName,
    List<AssetTypeChildResult> assetTypeChildResults
) {

    public static AssetTypeParentResult from(AssetType assetType) {
        return new AssetTypeParentResult(
            assetType.getId(),
            assetType.getName(),
            assetType.getAssetTypes().stream()
                .map(AssetTypeChildResult::from)
                .toList()
        );
    }
}
