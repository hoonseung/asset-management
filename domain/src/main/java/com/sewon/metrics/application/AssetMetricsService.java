package com.sewon.metrics.application;

import com.sewon.metrics.dto.AssetMetrics;
import com.sewon.metrics.dto.AssetStockTakingMetrics;
import com.sewon.metrics.repository.AssetMetricsRepository;
import com.sewon.metrics.repository.AssetStockTakingMetricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AssetMetricsService {

    private final AssetMetricsRepository assetMetricsRepository;
    private final AssetStockTakingMetricsRepository assetStockTakingMetricsRepository;


    public AssetMetrics getAssetMetrics(Long affiliationId) {
        return AssetMetrics.of(
            assetMetricsRepository.getCountByAffiliationId(affiliationId),
            assetMetricsRepository.getRentedByAffiliationId(affiliationId),
            assetMetricsRepository.getRentableAffiliationId(affiliationId)
        );
    }

    public AssetStockTakingMetrics getAssetStockTakingMetrics(Long affiliationId) {
        return AssetStockTakingMetrics.of(
            assetMetricsRepository.getCountByAffiliationId(affiliationId),
            assetStockTakingMetricsRepository.getCompleteStockTakingItemDuringMonth(affiliationId)
        );
    }
}
