package com.sewon.jpa.assetlocation;

import com.sewon.assetlocation.model.AssetLocation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetLocationJpaRepository extends JpaRepository<AssetLocation, Long> {


    List<AssetLocation> findByAffiliationId(Long id);


}
