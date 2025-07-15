package com.sewon.asset.dto.properties;

import com.sewon.account.model.Account;
import com.sewon.asset.constant.AssetDivision;
import com.sewon.asset.constant.AssetStatus;
import com.sewon.asset.model.Asset;
import com.sewon.asset.model.ElectronicAsset;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assettype.model.AssetType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;


@Getter
public class ElectronicAssetProperties extends AssetProperties {

    private final String cpu;
    private final Integer ram;
    private final BigDecimal storage;
    private final String gpu;


    public ElectronicAssetProperties(Long locationId,
        Integer division, Long parentTypeId, Long childTypeId, Integer assetStatus,
        String manufacturer,
        String model, LocalDateTime acquisitionDate, Integer acquisitionPrice, String cpu,
        Integer ram,
        BigDecimal storage, String gpu) {
        super(locationId, division, parentTypeId, childTypeId, assetStatus,
            manufacturer, model, acquisitionDate, acquisitionPrice);
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.gpu = gpu;
    }

    @Override
    public Asset toAsset(AssetType assetType, Account account, AssetLocation assetLocation) {
        return ElectronicAsset.of(
            AssetDivision.fromValue(getDivision()),
            AssetStatus.fromValue(getAssetStatus()),
            getManufacturer(),
            getModel(),
            getAcquisitionPrice(),
            getAcquisitionDate(),
            assetType,
            account,
            assetLocation,
            cpu,
            ram,
            storage,
            gpu
        );
    }

    public static ElectronicAssetProperties of(Long locationId, Integer division, Long parentTypeId,
        Long childTypeId,
        Integer assetStatus, String manufacturer, String model, LocalDateTime acquisitionDate,
        Integer acquisitionPrice, String cpu, Integer ram, BigDecimal storage, String gpu) {
        return new ElectronicAssetProperties(
            locationId, division, parentTypeId, childTypeId,
            assetStatus, manufacturer, model, acquisitionDate, acquisitionPrice,
            cpu, ram, storage, gpu);
    }

    @Override
    public Asset updateAsset(Asset asset, AssetLocation assetLocation, AssetType assetType) {
        AssetLocation newAssetLocation = asset.getAssetLocation();
        AssetType newAssetType = asset.getAssetType();
        if (assetLocation != null) {
            newAssetLocation = assetLocation;
        }
        asset.setAssetDivision(AssetDivision.fromValue(super.division));
        if (assetType != null) {
            newAssetType = assetType;
        }

        return new ElectronicAsset(asset.getId(), AssetDivision.fromValue(super.division),
            AssetStatus.fromValue(super.assetStatus), super.manufacturer, super.model,
            super.acquisitionPrice, super.acquisitionDate, newAssetType,
            asset.getAccount(), newAssetLocation, asset.getBarcode(), cpu, ram, storage, gpu);
    }
}
