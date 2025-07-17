package com.sewon.assettype.repository;

import com.sewon.assettype.model.AssetType;
import java.util.List;
import java.util.Optional;

public interface AssetTypeRepository {

    void save(AssetType assetType);

    Optional<AssetType> findById(Long id);

    Optional<AssetType> findByParentAndChildType(Long parentId, Long childId);

    Optional<AssetType> findByName(String name);

    List<AssetType> findAllParentType();

    List<AssetType> findAll();

    List<AssetType> findAllById(Long id);

    void deleteById(Long id);

    boolean existsAssetByAssetTypeId(Long id);

}
