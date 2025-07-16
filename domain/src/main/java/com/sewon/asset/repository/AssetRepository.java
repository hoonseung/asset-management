package com.sewon.asset.repository;

import com.sewon.asset.dto.result.AllAssetResult;
import com.sewon.asset.model.Asset;
import java.util.List;
import java.util.Optional;

public interface AssetRepository {

    Asset save(Asset asset);

    List<Asset> saveAll(List<Asset> assets);

    Optional<Asset> findById(Long id);

    Optional<AllAssetResult> findAssetDtoByBarcode(String value);

    List<AllAssetResult> findAll(int size);

    Optional<Asset> findAssetByBarcode(String value);

    List<Asset> findAllAssetByBarcode(List<String> values);

    void deleteAllById(List<Long> ids);


}
