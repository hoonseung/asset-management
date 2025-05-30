package com.sewon.jpa.barcode;

import com.sewon.asset.model.Asset;
import com.sewon.barcode.model.Barcode;
import com.sewon.barcode.repository.BarcodeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaBarcodeRepository implements BarcodeRepository {

    private final BarcodeJpaRepository barcodeJpaRepository;

    @Override
    public Barcode save(Barcode barcode) {
        return barcodeJpaRepository.save(barcode);
    }

    @Override
    public Optional<Barcode> findByValue(String value) {
        return barcodeJpaRepository.findBarcodesByValue(value);
    }

    @Override
    public Optional<Asset> findAssetByValue(String value) {
        return barcodeJpaRepository.findAssetByBarcode(value);
    }

    @Override
    public void deleteById(Long id) {
        barcodeJpaRepository.deleteById(id);
    }
}
