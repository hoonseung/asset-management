package com.sewon.jpa.stocktaking;

import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.stocktaking.model.AssetStockTaking;
import com.sewon.stocktaking.repository.AssetStockTakingRepository;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetStockTakingRepository implements AssetStockTakingRepository {

    private final AssetStockTakingJpaRepository assetStockTakingJpaRepository;

    @Override
    public AssetStockTaking save(AssetStockTaking assetStockTaking) {
        return assetStockTakingJpaRepository.save(assetStockTaking);
    }

    @Override
    public Optional<AssetStockTaking> findById(Long id) {
        return assetStockTakingJpaRepository.findById(id);
    }

    @Override
    public Optional<AssetStockTaking> findByLocationAndAuditingDate(AssetLocation assetLocation,
        LocalDate date) {
        return assetStockTakingJpaRepository.findByAssetLocationAndAuditingDate(assetLocation,
            date);
    }

    @Override
    public void deleteById(Long id) {
        assetStockTakingJpaRepository.deleteById(id);
    }
}
