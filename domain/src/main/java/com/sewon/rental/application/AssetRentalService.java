package com.sewon.rental.application;

import com.sewon.rental.model.AssetRental;
import com.sewon.rental.repository.AssetRentalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AssetRentalService {

    private final AssetRentalRepository assetRentalRepository;


    @Transactional
    public AssetRental registerAssetRental(AssetRental assetRental) {
        return assetRentalRepository.save(assetRental);
    }

    @Transactional
    public void deleteAssetRentalById(Long id) {
        assetRentalRepository.deleteById(id);
    }

    public AssetRental findAssetRentalById(Long id) {
        return assetRentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("자산대여를 찾을 수 없습니다."));
    }

    public List<AssetRental> findAllAssetRental() {
        return assetRentalRepository.findAll();
    }

    public List<AssetRental> findAllAssetRentalByAccountName(String username) {
        return assetRentalRepository.findAllByAccountName(username);
    }


}
