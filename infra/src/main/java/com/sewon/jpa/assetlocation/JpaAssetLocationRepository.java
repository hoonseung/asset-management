package com.sewon.jpa.assetlocation;

import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assetlocation.repository.AssetLocationRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetLocationRepository implements AssetLocationRepository {

    private final AssetLocationJpaRepository assetLocationJpaRepository;

    @Override
    public void save(AssetLocation assetLocation) {
        assetLocationJpaRepository.save(assetLocation);
    }

    @Override
    public Optional<AssetLocation> findById(Long id) {
        return assetLocationJpaRepository.findById(id);
    }

    @Override
    public List<AssetLocation> findAllByAffiliationId(Long id) {
        return assetLocationJpaRepository.findByAffiliationId(id);
    }

    @Override
    public Optional<AssetLocation> findByLocation(String location) {
        return assetLocationJpaRepository.findByLocation(location);
    }

    @Override
    public List<AssetLocation> findAll() {
        return assetLocationJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        assetLocationJpaRepository.deleteById(id);
    }
}
