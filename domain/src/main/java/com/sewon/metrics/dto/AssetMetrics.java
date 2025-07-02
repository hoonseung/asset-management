package com.sewon.metrics.dto;

public record AssetMetrics(
    Long totalCount,
    Long rentedCount,
    Long rentableCount
) {

    public static AssetMetrics of(Long totalCount, Long rentedCount, Long rentableCount) {
        return new AssetMetrics(totalCount, rentedCount, rentableCount);
    }

}
