package com.sewon.stocktaking.repository;

import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.stocktaking.model.AssetStockTaking;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssetStockTakingRepository {

    AssetStockTaking save(AssetStockTaking assetStockTaking);

    Optional<AssetStockTaking> findById(Long id);

    Optional<AssetStockTaking> findByLocationAndAuditingDate(AssetLocation location,
        LocalDate date);

    Optional<AssetStockTaking> findByAssetLocationId(Long locationId);

    List<AssetStockTaking> findByLocationIdAndBetweenStartingDate(Long locationId,
        LocalDate fromDate,
        LocalDate toDate);

    void deleteById(Long id);
}
