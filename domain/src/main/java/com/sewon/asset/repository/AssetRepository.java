package com.sewon.asset.repository;

import com.sewon.asset.model.Asset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AssetRepository {

    Asset save(Asset asset);

    List<Asset> saveAll(List<Asset> assets);

    Optional<Asset> findById(Long id);

    Optional<Asset> findByBarcode(String value);

    List<Asset> findAll();

    List<Asset> findAll(int size);

    List<Asset> findAllByLocation(String location, int size);

    List<Asset> findAllByLocationAndChildType(String location, String childType,
        int size);

    List<Asset> findAllByLocationAndChildTypeBetween(String location, String childType,
        LocalDateTime after,
        LocalDateTime before, int size);

    List<Asset> findAllByLocationAndParentType(String location, String parentType, int size);

    List<Asset> findAllByLocationAndParentTypeBetween(String location,
        String parentType, LocalDateTime after,
        LocalDateTime before, int size);

    List<Asset> findAllBetween(LocalDateTime after, LocalDateTime before, int size);

    void deleteById(Long id);
}
