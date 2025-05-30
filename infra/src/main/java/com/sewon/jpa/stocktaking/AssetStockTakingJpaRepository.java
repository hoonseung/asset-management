package com.sewon.jpa.stocktaking;

import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.stocktaking.model.AssetStockTaking;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetStockTakingJpaRepository extends JpaRepository<AssetStockTaking, Long> {

    Optional<AssetStockTaking> findByAssetLocationAndAuditingDate(AssetLocation assetLocation,
        LocalDate date);
}
