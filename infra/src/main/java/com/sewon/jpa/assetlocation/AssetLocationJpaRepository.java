package com.sewon.jpa.assetlocation;

import com.sewon.assetlocation.model.AssetLocation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssetLocationJpaRepository extends JpaRepository<AssetLocation, Long> {


    List<AssetLocation> findByAffiliationId(Long id);

    @Query("select case when count(a) > 0 then true else false end from Asset a where a.assetLocation.id = :id")
    boolean existsAssetByAssetLocationId(Long id);
}
