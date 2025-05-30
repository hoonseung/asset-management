package com.sewon.assettype.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.assettype.model.AssetType;
import java.util.List;

public record AssetTypeOneParentResponse(

    Long parentId,
    String name,
    @JsonProperty("childList")
    @JsonInclude(Include.NON_EMPTY)
    List<AssetTypeOneChildResponse> assetTypeOneResponses

) {


    public static AssetTypeOneParentResponse from(AssetType assetType) {
        return new AssetTypeOneParentResponse(
            assetType.getId(),
            assetType.getName(),
            assetType.getAssetTypes().stream()
                .map(AssetTypeOneChildResponse::from)
                .toList()
        );
    }
}

