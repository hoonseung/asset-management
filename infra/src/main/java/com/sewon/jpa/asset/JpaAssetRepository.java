package com.sewon.jpa.asset;

import com.sewon.asset.model.Asset;
import com.sewon.asset.repository.AssetRepository;
import com.sewon.jpa.barcode.JpaBarcodeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetRepository implements AssetRepository {

    private final AssetJpaRepository assetJpaRepository;

    private final JpaBarcodeRepository barcodeJpaRepository;

    @Override
    public Asset save(Asset asset) {
        return assetJpaRepository.save(asset);
    }

    @Override
    public List<Asset> saveAll(List<Asset> assets) {
        return assetJpaRepository.saveAll(assets);
    }

    @Override
    public Optional<Asset> findById(Long id) {
        return assetJpaRepository.findById(id);
    }

    @Override
    public List<Asset> findAll() {
        return assetJpaRepository.findAll();
    }

    @Override
    public List<Asset> findAll(int size) {
        return assetJpaRepository.findAll(PageRequest.of(0, size))
            .toList();
    }

    @Override
    public Optional<Asset> findByBarcode(String value) {
        return barcodeJpaRepository.findAssetByValue(value);
    }

    @Override
    public List<Asset> findAllByLocationId(Long locationId) {
        return assetJpaRepository.findAllByAssetLocationId(locationId);
    }

    @Override
    public List<Asset> findAllByLocationAndChildType(String location, String childType, int size) {
        return assetJpaRepository.findAllByLocationAndChildType(location, childType,
            PageRequest.of(0, size));
    }

    @Override
    public List<Asset> findAllByLocationAndChildTypeBetween(String location, String childType,
        LocalDateTime after, LocalDateTime before, int size) {
        return assetJpaRepository.findAllByLocationAndChildTypeBetween(location, childType,
            after, before, PageRequest.of(0, size));
    }

    @Override
    public List<Asset> findAllByLocationAndParentType(String location, String parentType,
        int size) {
        return assetJpaRepository.findAllByLocationAndParentType(location, parentType,
            PageRequest.of(0, size));
    }

    @Override
    public List<Asset> findAllByLocationAndParentTypeBetween(String location, String parentType,
        LocalDateTime after, LocalDateTime before, int size) {
        return assetJpaRepository.findAllByLocationAndParentTypeBetween(location, parentType,
            after, before, PageRequest.of(0, size));
    }

    @Override
    public List<Asset> findAllBetween(LocalDateTime after, LocalDateTime before, int size) {
        return assetJpaRepository.findAllByCreatedDateBetween(after, before,
            PageRequest.of(0, size));
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        assetJpaRepository.deleteAllByIdIn(ids);
    }
}
