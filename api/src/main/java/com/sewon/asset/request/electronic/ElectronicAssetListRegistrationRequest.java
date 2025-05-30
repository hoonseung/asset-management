package com.sewon.asset.request.electronic;

import com.sewon.asset.dto.AssetProperties;
import java.util.List;

public record ElectronicAssetListRegistrationRequest(

    List<ElectronicAssetRegistrationRequest> requests
) {


    public List<AssetProperties> toGeneralAssetPropertiesList() {
        return requests.stream()
            .map(ElectronicAssetRegistrationRequest::toElectronicAssetProperties)
            .toList();
    }

}
