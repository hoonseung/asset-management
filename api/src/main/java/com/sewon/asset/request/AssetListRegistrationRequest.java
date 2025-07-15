package com.sewon.asset.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.asset.dto.properties.AssetProperties;
import java.util.List;

public record AssetListRegistrationRequest(

    @JsonProperty("list")
    List<AssetRegistrationRequest> requests
) {


    public List<AssetProperties> toGeneralAssetPropertiesList() {
        return requests.stream()
            .map(AssetRegistrationRequest::toGeneralAssetProperties)
            .toList();
    }

}
