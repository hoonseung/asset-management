package com.sewon.barcode.repository;

import com.sewon.asset.model.Asset;
import com.sewon.barcode.model.Barcode;
import java.util.Optional;

public interface BarcodeRepository {

    Barcode save(Barcode barcode);

    Optional<Barcode> findByValue(String value);

    Optional<Asset> findAssetByValue(String value);

    void deleteById(Long id);
}
