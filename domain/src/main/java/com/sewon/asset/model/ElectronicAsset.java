package com.sewon.asset.model;


import static lombok.AccessLevel.PROTECTED;

import com.sewon.account.model.Account;
import com.sewon.asset.constant.AssetDivision;
import com.sewon.asset.constant.AssetStatus;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assettype.model.AssetType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter
@DiscriminatorValue("ELECTRONIC")
@Table(name = "electronic_asset")
@Entity
public class ElectronicAsset extends Asset {

    @Column(name = "cpu", length = 50)
    private String cpu;

    @Column(name = "ram", columnDefinition = "SMALLINT")
    private Integer ram;

    @Column(precision = 6, scale = 3)
    private BigDecimal storage;

    @Column(name = "gpu", length = 50)
    private String gpu;


    public ElectronicAsset(AssetDivision assetDivision, AssetStatus assetStatus,
        String manufacturer, String model, Integer acquisitionPrice, LocalDateTime acquisitionDate,
        AssetType assetType, Account account, AssetLocation assetLocation,
        String cpu, Integer ram, BigDecimal storage, String gpu) {
        super(null, assetDivision, assetStatus, manufacturer, model, acquisitionPrice,
            acquisitionDate, assetType, account, assetLocation, null);
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.gpu = gpu;
    }

    public static ElectronicAsset of(AssetDivision assetDivision, AssetStatus assetStatus,
        String manufacturer, String model, Integer acquisitionPrice, LocalDateTime acquisitionDate,
        AssetType assetType, Account account, AssetLocation assetLocation,
        String cpu, Integer ram, BigDecimal storage, String gpu) {
        return new ElectronicAsset(
            assetDivision,
            assetStatus,
            manufacturer,
            model,
            acquisitionPrice,
            acquisitionDate,
            assetType,
            account,
            assetLocation,
            cpu, ram, storage, gpu);
    }
}
