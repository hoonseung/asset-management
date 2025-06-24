package com.sewon.asset.request.electronic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.asset.dto.AssetProperties;
import java.util.List;

public record ElectronicAssetListRegistrationRequest(

    @JsonProperty("list")
    List<ElectronicAssetRegistrationRequest> requests
) {


    public List<AssetProperties> toGeneralAssetPropertiesList() {
        return requests.stream()
            .map(ElectronicAssetRegistrationRequest::toElectronicAssetProperties)
            .toList();
    }

}
