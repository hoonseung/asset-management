package com.sewon.asset.dto.properties;

import com.sewon.account.model.Account;
import com.sewon.asset.constant.AssetDivision;
import com.sewon.asset.constant.AssetStatus;
import com.sewon.asset.model.Asset;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assettype.model.AssetType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GeneralAssetProperties extends AssetProperties {

    public GeneralAssetProperties(Long locationId,
        Integer division, Long parentTypeId, Long childTypeId, Integer assetStatus,
        String manufacturer,
        String model, LocalDateTime acquisitionDate, Integer acquisitionPrice) {
        super(locationId, division, parentTypeId, childTypeId, assetStatus,
            manufacturer, model, acquisitionDate, acquisitionPrice);
    }

    @Override
    public Asset toAsset(AssetType assetType, Account account, AssetLocation assetLocation) {
        return Asset.of(
            AssetDivision.fromValue(getDivision()),
            AssetStatus.fromValue(getAssetStatus()),
            getManufacturer(),
            getModel(),
            getAcquisitionPrice(),
            getAcquisitionDate(),
            assetType,
            account,
            assetLocation
        );
    }

    public static GeneralAssetProperties of(Long locationId,
        Integer division, Long parentTypeId, Long childTypeId,
        Integer assetStatus,
        String manufacturer, String model, LocalDateTime acquisitionDate,
        Integer acquisitionPrice) {
        return new GeneralAssetProperties(
            locationId, division, parentTypeId,
            childTypeId, assetStatus, manufacturer, model, acquisitionDate,
            acquisitionPrice);
    }

    @Override
    public Asset updateAsset(Asset asset, AssetLocation assetLocation, AssetType assetType) {
        if (assetLocation != null) {
            asset.setAssetLocation(assetLocation);
        }
        asset.setAssetDivision(AssetDivision.fromValue(division));
        if (assetType != null) {
            asset.setAssetType(assetType);
        }
        asset.setAssetStatus(AssetStatus.fromValue(assetStatus));
        asset.setManufacturer(manufacturer);
        asset.setModel(model);
        asset.setAcquisitionDate(acquisitionDate);
        asset.setAcquisitionPrice(acquisitionPrice);
        return asset;
    }
}
