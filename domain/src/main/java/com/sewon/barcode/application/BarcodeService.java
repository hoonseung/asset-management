package com.sewon.barcode.application;

import static com.sewon.barcode.exception.BarcodeErrorCode.ASSET_BARCODE_NOT_FOUND;
import static com.sewon.barcode.exception.BarcodeErrorCode.BARCODE_NOT_FOUND;

import com.sewon.asset.model.Asset;
import com.sewon.barcode.model.Barcode;
import com.sewon.barcode.repository.BarcodeRepository;
import com.sewon.common.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BarcodeService {


    private final BarcodeRepository barcodeRepository;

    @Transactional
    public Barcode registerBarcode(Barcode barcode) {
        return barcodeRepository.save(barcode);
    }

    @Transactional
    public void deleteBarcodeById(Long id) {
        barcodeRepository.deleteById(id);
    }

    public Barcode findBarcodeByValue(String value) {
        return barcodeRepository.findByValue(value)
            .orElseThrow(() -> new DomainException(BARCODE_NOT_FOUND));
    }

    public Asset findAssetByValue(String value) {
        return barcodeRepository.findAssetByValue(value)
            .orElseThrow(() -> new DomainException(ASSET_BARCODE_NOT_FOUND));
    }
}
