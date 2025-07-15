package com.sewon.jpa.asset;

import com.sewon.asset.dto.result.AllAssetResult;
import com.sewon.asset.model.Asset;
import com.sewon.asset.repository.AssetRepository;
import com.sewon.jpa.barcode.BarcodeJpaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetRepository implements AssetRepository {

    private final AssetJpaRepository assetJpaRepository;
    private final BarcodeJpaRepository barcodeJpaRepository;

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

    // dto로 반환하기
    @Override
    public List<AllAssetResult> findAll(int size) {
        return assetJpaRepository.findAllAsset(PageRequest.of(0, size));
    }

    @Override
    public Optional<AllAssetResult> findAllAssetByBarcode(String value) {
        return assetJpaRepository.findAssetByBarcode(value);
    }

    @Override
    public Optional<Asset> findAssetByValue(String value) {
        return barcodeJpaRepository.findAssetByBarcode(value);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        assetJpaRepository.deleteAllByIdIn(ids);
    }
}
