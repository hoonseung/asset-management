package com.sewon.asset.repository;

import com.sewon.asset.dto.AssetResult;
import com.sewon.asset.dto.AssetSearchProperties;
import java.util.List;

public interface AssetSearchRepository {

    List<AssetResult> searchAssets(AssetSearchProperties properties);

    List<AssetResult> searchRentalEnableAssets(AssetSearchProperties properties);
}
