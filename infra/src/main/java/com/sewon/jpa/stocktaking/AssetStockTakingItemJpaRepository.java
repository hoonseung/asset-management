package com.sewon.jpa.stocktaking;

import com.sewon.stocktaking.model.AssetStockTakingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetStockTakingItemJpaRepository extends
    JpaRepository<AssetStockTakingItem, Long> {

}
