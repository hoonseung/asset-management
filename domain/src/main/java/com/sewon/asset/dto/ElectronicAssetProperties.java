package com.sewon.asset.dto;

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


    public ElectronicAssetProperties(String corporation, String department, String location,
        Integer division, String parentType, String childType, Integer assetStatus,
        String manufacturer,
        String model, LocalDateTime acquisitionDate, Integer acquisitionPrice, String cpu,
        Integer ram,
        BigDecimal storage, String gpu) {
        super(corporation, department, location, division, parentType, childType, assetStatus,
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

    public static ElectronicAssetProperties of(String corporation, String department,
        String location, Integer division, String parentType, String childType,
        Integer assetStatus, String manufacturer, String model, LocalDateTime acquisitionDate,
        Integer acquisitionPrice, String cpu, Integer ram, BigDecimal storage, String gpu) {
        return new ElectronicAssetProperties(
            corporation, department, location, division, parentType, childType,
            assetStatus, manufacturer, model, acquisitionDate, acquisitionPrice,
            cpu, ram, storage, gpu);
    }


}
