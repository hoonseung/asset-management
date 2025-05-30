package com.sewon.barcode.application;

import com.sewon.asset.model.Asset;
import com.sewon.barcode.model.Barcode;
import com.sewon.barcode.repository.BarcodeRepository;
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
            .orElseThrow(() -> new RuntimeException("존재하지 않는 바코드입니다."));
    }

    public Asset findAssetByValue(String value) {
        return barcodeRepository.findAssetByValue(value)
            .orElseThrow(() -> new RuntimeException("현재 바코드로 자산을 찾을 수 없습니다."));
    }
}
