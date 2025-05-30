package com.sewon.jpa.assetlocation;

import com.sewon.assetlocation.model.AssetLocation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetLocationJpaRepository extends JpaRepository<AssetLocation, Long> {

    Optional<AssetLocation> findByLocation(String location);

    List<AssetLocation> findByAffiliationId(Long id);
}
