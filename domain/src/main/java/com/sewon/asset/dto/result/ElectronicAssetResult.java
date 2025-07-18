package com.sewon.asset.dto.result;

import com.sewon.asset.model.ElectronicAsset;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ElectronicAssetResult extends AssetResult {

    private final String cpu;
    private final Integer ram;
    private final BigDecimal storage;
    private final String gpu;


    public ElectronicAssetResult(Long id, String barcode, String corporation, String department,
        String location, String division, Long assetTypeId, String parentCategory,
        String childCategory,
        String status, String manufacturer, String model, LocalDate acquisitionDate,
        Integer acquisitionPrice, String registerName, String cpu, Integer ram, BigDecimal storage,
        String gpu) {
        super(id, barcode, corporation, department, location, division, assetTypeId, parentCategory,
            childCategory, status, manufacturer, model, acquisitionDate, acquisitionPrice,
            registerName);
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.gpu = gpu;
    }

    public static ElectronicAssetResult from(ElectronicAsset asset) {
        return new ElectronicAssetResult(
            asset.getId(),
            asset.getBarcodeValue(),
            asset.getCorporationName(),
            asset.getDepartment(),
            asset.getLocation(),
            asset.getDivision(),
            asset.getAssetTypeId(),
            asset.getParentCategory(),
            asset.getChildCategory(),
            asset.getStatus(),
            asset.getManufacturer(),
            asset.getModel(),
            asset.getAcquisitionDate().toLocalDate(),
            asset.getAcquisitionPrice(),
            asset.getAccountName(),
            asset.getCpu(),
            asset.getRam(),
            asset.getStorage(),
            asset.getGpu()
        );
    }

    public static ElectronicAssetResult of(
        Long id,
        String barcode,
        String corporation,
        String department,
        String location,
        String division,
        Long assetTypeId,
        String parentCategory,
        String childCategory,
        String status,
        String manufacturer,
        String model,
        LocalDate acquisitionDate,
        Integer acquisitionPrice,
        String registerName,
        String cpu,
        Integer ram,
        BigDecimal storage,
        String gpu
    ) {
        return new ElectronicAssetResult(
            id, barcode, corporation, department, location, division, assetTypeId,
            parentCategory, childCategory,
            status, manufacturer, model, acquisitionDate, acquisitionPrice,
            registerName, cpu, ram, storage, gpu
        );
    }
}
