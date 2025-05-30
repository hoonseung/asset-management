package com.sewon.jpa.stocktaking;

import com.sewon.stocktaking.model.AssetStockTakingItem;
import com.sewon.stocktaking.repository.AssetStockTakingItemRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetStockTakingItemRepository implements AssetStockTakingItemRepository {

    private final AssetStockTakingItemJpaRepository assetStockTakingItemJpaRepository;

    @Override
    public AssetStockTakingItem save(AssetStockTakingItem assetStockTakingItem) {
        return assetStockTakingItemJpaRepository.save(assetStockTakingItem);
    }

    @Override
    public void saveAll(List<AssetStockTakingItem> assetStockTakingItems) {
        assetStockTakingItemJpaRepository.saveAll(assetStockTakingItems);
    }

    @Override
    public List<AssetStockTakingItem> findAll(String department, String location, String assetType,
        LocalDateTime after, LocalDateTime before) {
        return List.of();
    }

    @Override
    public List<AssetStockTakingItem> findAll(String department, String location, String assetType,
        boolean isCompleted, LocalDateTime after, LocalDateTime before) {
        return List.of();
    }

    @Override
    public List<AssetStockTakingItem> findAll(String department, LocalDateTime after,
        LocalDateTime before) {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {
        assetStockTakingItemJpaRepository.deleteById(id);
    }
}
