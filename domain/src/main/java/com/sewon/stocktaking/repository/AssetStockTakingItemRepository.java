package com.sewon.stocktaking.repository;

import com.sewon.stocktaking.model.AssetStockTakingItem;
import java.time.LocalDateTime;
import java.util.List;

public interface AssetStockTakingItemRepository {

    AssetStockTakingItem save(AssetStockTakingItem assetStockTakingItem);

    void saveAll(List<AssetStockTakingItem> assetStockTakingItems);

    List<AssetStockTakingItem> findAll(String department, String location, String assetType,
        LocalDateTime after, LocalDateTime before);

    List<AssetStockTakingItem> findAll(String department, String location, String assetType,
        boolean isCompleted, LocalDateTime after, LocalDateTime before);

    List<AssetStockTakingItem> findAll(String department, LocalDateTime after,
        LocalDateTime before);

    void deleteById(Long id);
}
