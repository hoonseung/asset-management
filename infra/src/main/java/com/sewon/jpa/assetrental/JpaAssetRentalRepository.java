package com.sewon.jpa.assetrental;

import com.sewon.rental.model.AssetRental;
import com.sewon.rental.repository.AssetRentalRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetRentalRepository implements AssetRentalRepository {

    private final AssetRentalJpaRepository assetRentalJpaRepository;

    @Override
    public AssetRental save(AssetRental assetRental) {
        return assetRentalJpaRepository.save(assetRental);
    }

    @Override
    public Optional<AssetRental> findById(Long id) {
        return assetRentalJpaRepository.findById(id);
    }

    @Override
    public List<AssetRental> findAll() {
        return assetRentalJpaRepository.findAll();
    }

    @Override
    public List<AssetRental> findAllByAccountName(String username) {
        return assetRentalJpaRepository.findAllByAccountName(username);
    }

    @Override
    public void deleteById(Long id) {
        assetRentalJpaRepository.deleteById(id);
    }
}
