package com.sewon.asset.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.asset.model.Asset;
import java.util.List;

public record AssetListResponse(
    @JsonProperty("list")
    List<AssetOneResponse> responses
) {

    public static AssetListResponse from(List<Asset> assets) {
        return new AssetListResponse(
            assets.stream().map(AssetOneResponse::from)
                .toList());
    }


    public int listResponseSize() {
        return responses.size();
    }
}
