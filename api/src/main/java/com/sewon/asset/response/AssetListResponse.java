package com.sewon.asset.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.asset.dto.result.AllAssetResult;
import java.util.List;

public record AssetListResponse(
    @JsonProperty("list")
    List<? extends AssetOneResponse> responses
) {


    public static AssetListResponse fromAllAsset(List<AllAssetResult> assets) {
        return new AssetListResponse(
            assets.stream().map(AssetOneResponse::from)
                .toList());
    }

    public int listResponseSize() {
        return responses.size();
    }
}
