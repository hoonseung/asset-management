package com.sewon.metrics.repository;

public interface AssetMetricsRepository {

    Long getCountByAffiliationId(Long affiliationId);

    Long getRentedByAffiliationId(Long affiliationId);

    Long getRentableAffiliationId(Long affiliationId);
}
