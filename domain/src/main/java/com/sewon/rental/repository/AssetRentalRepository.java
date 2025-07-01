package com.sewon.rental.repository;

import com.sewon.rental.constant.RentalStatus;
import com.sewon.rental.model.AssetRental;
import java.util.List;
import java.util.Optional;

public interface AssetRentalRepository {

    AssetRental save(AssetRental assetRental);

    Optional<AssetRental> findById(Long id);

    List<AssetRental> findAll();

    List<AssetRental> findAllByAccountName(String username);

    List<AssetRental> findAllByAccountNameAndRentalStatus(String username, RentalStatus status);

    List<AssetRental> findAllByRentalStatusAndAssetAffiliation(RentalStatus status,
        Long affiliationId);

    List<AssetRental> findAllByRentalStatusAndMyAffiliation(RentalStatus status,
        Long affiliationId);

    List<AssetRental> findAllByIds(List<Long> ids);

    void deleteById(Long id);

}
