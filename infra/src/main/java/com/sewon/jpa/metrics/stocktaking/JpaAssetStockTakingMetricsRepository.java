package com.sewon.jpa.metrics.stocktaking;

import com.sewon.jpa.stocktaking.AssetStockTakingItemJpaRepository;
import com.sewon.metrics.repository.AssetStockTakingMetricsRepository;
import com.sewon.stocktaking.constant.AssetCheckingStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetStockTakingMetricsRepository implements AssetStockTakingMetricsRepository {

    private final AssetStockTakingItemJpaRepository assetStockTakingItemJpaRepository;

    @Override
    public Long getCompleteStockTakingItemDuringMonth(Long affiliationId) {
        return assetStockTakingItemJpaRepository
            .getCompleteStockTakingItemDuringMonth(affiliationId,
                LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                    .withNano(0),
                LocalDateTime.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
                    .withHour(23).withMinute(59).withSecond(59).withNano(999_999_999),
                AssetCheckingStatus.DISABLE);
    }
}
