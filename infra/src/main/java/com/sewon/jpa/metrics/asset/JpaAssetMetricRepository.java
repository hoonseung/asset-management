package com.sewon.jpa.metrics.asset;

import static com.sewon.rental.constant.RentalStatus.EXPIRE;
import static com.sewon.rental.constant.RentalStatus.RENT;
import static com.sewon.rental.constant.RentalStatus.REQUEST_RENTAL;
import static com.sewon.rental.constant.RentalStatus.REQUEST_RETURN;

import com.sewon.jpa.asset.AssetJpaRepository;
import com.sewon.jpa.assetrental.AssetRentalJpaRepository;
import com.sewon.metrics.repository.AssetMetricsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetMetricRepository implements AssetMetricsRepository {

    private final AssetJpaRepository assetJpaRepository;
    private final AssetRentalJpaRepository assetRentalJpaRepository;

    @Override
    public Long getCountByAffiliationId(Long affiliationId) {
        return assetJpaRepository.getCountByAffiliationId(affiliationId);
    }

    @Override
    public Long getRentedByAffiliationId(Long affiliationId) {
        return assetRentalJpaRepository.getRentedByAffiliationId(affiliationId,
            RENT);
    }

    @Override
    public Long getRentableAffiliationId(Long affiliationId) {
        return assetRentalJpaRepository.getRentableByAffiliationId(affiliationId,
            List.of(REQUEST_RENTAL, RENT, REQUEST_RETURN, EXPIRE));
    }
}
