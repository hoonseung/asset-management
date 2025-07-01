package com.sewon.jpa.stocktaking;

import com.sewon.stocktaking.constant.AssetCheckingStatus;
import com.sewon.stocktaking.model.AssetStockTakingItem;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssetStockTakingItemJpaRepository extends
    JpaRepository<AssetStockTakingItem, Long> {


    List<AssetStockTakingItem> findAllByAssetStockTakingId(Long stockTakingId);

    @Query(
        "select count(it.asset) from AssetStockTakingItem it where it.assetLocation.affiliation.id = :affiliationId "
            + "and it.createdDate between :startDate and :endDate and it.assetCheckingStatus != :status")
    Long getCompleteStockTakingItemDuringMonth(Long affiliationId, LocalDateTime startDate,
        LocalDateTime endDate, AssetCheckingStatus status);
}
