package com.sewon.asset.repository;

import com.sewon.asset.dto.AssetSearchProperties;
import com.sewon.asset.model.Asset;
import java.util.List;

public interface AssetSearchRepository {

    List<Asset> searchAssets(AssetSearchProperties properties);
}
