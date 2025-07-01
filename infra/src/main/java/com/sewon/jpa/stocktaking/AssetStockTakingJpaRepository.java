package com.sewon.jpa.stocktaking;

import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.stocktaking.model.AssetStockTaking;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetStockTakingJpaRepository extends JpaRepository<AssetStockTaking, Long> {

    Optional<AssetStockTaking> findByAssetLocationAndAuditingDate(AssetLocation assetLocation,
        LocalDate date);

    List<AssetStockTaking> findByAssetLocationIdAndStartingDateBetween
        (Long locationId, LocalDate auditingDateAfter, LocalDate auditingDateBefore);

    Optional<AssetStockTaking> findByAssetLocationId(Long assetLocationId);
}
