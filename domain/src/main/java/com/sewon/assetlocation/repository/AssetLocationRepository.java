package com.sewon.assetlocation.repository;

import com.sewon.assetlocation.model.AssetLocation;
import java.util.List;
import java.util.Optional;

public interface AssetLocationRepository {

    void save(AssetLocation assetLocation);

    Optional<AssetLocation> findById(Long id);

    List<AssetLocation> findAll();

    List<AssetLocation> findAllByAffiliationId(Long id);

    void deleteById(Long id);

    boolean existsAssetByAssetLocationId(Long id);
}
