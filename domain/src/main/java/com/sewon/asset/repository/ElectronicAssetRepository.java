package com.sewon.asset.repository;

import com.sewon.asset.model.ElectronicAsset;
import java.util.List;
import java.util.Optional;

public interface ElectronicAssetRepository {

    ElectronicAsset save(ElectronicAsset electronicAsset);

    Optional<ElectronicAsset> findById(Long id);

    List<ElectronicAsset> findAll();

    void deleteById(Long id);
}
