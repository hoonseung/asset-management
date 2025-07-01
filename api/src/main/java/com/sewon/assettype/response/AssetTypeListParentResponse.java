package com.sewon.assettype.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.assettype.dto.AssetTypeParentResult;
import java.util.List;

public record AssetTypeListParentResponse(

    @JsonProperty("parentList")
    List<AssetTypeOneParentResponse> assetTypeOneParentResponses

) {


    public static AssetTypeListParentResponse from(List<AssetTypeParentResult> assetType) {
        return new AssetTypeListParentResponse(assetType.stream()
            .map(AssetTypeOneParentResponse::from)
            .toList()
        );
    }
}

