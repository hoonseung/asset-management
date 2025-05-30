package com.sewon.assetlocation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.assetlocation.model.AssetLocation;
import java.util.List;

public record AssetLocationListResponse(
    @JsonProperty("assetLocationList")
    List<AssetLocationOneResponse> assetLocationOneResponses
) {

    public static AssetLocationListResponse from(List<AssetLocation> assetLocation) {
        return new AssetLocationListResponse(
            assetLocation.stream()
                .map(AssetLocationOneResponse::from)
                .toList()
        );
    }
}
