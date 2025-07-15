package com.sewon.asset.repository;

import com.sewon.asset.dto.properties.AssetSearchProperties;
import com.sewon.asset.dto.result.AllAssetResult;
import com.sewon.asset.dto.result.RentalResult;
import java.util.List;

public interface AssetSearchRepository {

    List<AllAssetResult> searchAssets(AssetSearchProperties properties);

    List<RentalResult> searchRentalEnableAssets(AssetSearchProperties properties);
}
