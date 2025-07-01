package com.sewon.jpa.assettype;

import com.sewon.assettype.model.AssetType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssetTypeJpaRepository extends JpaRepository<AssetType, Long> {

    Optional<AssetType> findByNameAndAssetTypeName(String childType, String parentType);

    Optional<AssetType> findByName(String name);

    @Query("select a from AssetType a where a.assetType.id is null ")
    List<AssetType> findAllParentType();

    List<AssetType> findAllByAssetTypeId(Long id);

}
