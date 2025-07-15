package com.sewon.asset.repository;

import com.sewon.asset.dto.result.AllAssetResult;
import com.sewon.asset.model.Asset;
import java.util.List;
import java.util.Optional;

public interface AssetRepository {

    Asset save(Asset asset);

    List<Asset> saveAll(List<Asset> assets);

    Optional<Asset> findById(Long id);

    Optional<AllAssetResult> findAllAssetByBarcode(String value);

    List<AllAssetResult> findAll(int size);

    Optional<Asset> findAssetByValue(String value);

    void deleteAllById(List<Long> ids);


}
