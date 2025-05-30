package com.sewon.assettype.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.assettype.model.AssetType;
import java.util.List;

public record AssetTypeListParentResponse(

    @JsonProperty("parentList")
    List<AssetTypeOneParentResponse> assetTypeOneParentResponses

) {


    public static AssetTypeListParentResponse from(List<AssetType> assetType) {
        return new AssetTypeListParentResponse(assetType.stream()
            .map(AssetTypeOneParentResponse::from)
            .toList()
        );
    }
}

