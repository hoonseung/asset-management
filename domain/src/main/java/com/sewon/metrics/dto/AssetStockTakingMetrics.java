package com.sewon.metrics.dto;

public record AssetStockTakingMetrics(
    Long totalCount,
    Long completeCount
) {

    public static AssetStockTakingMetrics of(Long totalCount, Long completeCount) {
        return new AssetStockTakingMetrics(totalCount, completeCount);
    }
}
