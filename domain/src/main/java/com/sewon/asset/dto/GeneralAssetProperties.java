package com.sewon.asset.dto;

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

    public GeneralAssetProperties(String corporation, String department, String location,
        Integer division, String parentType, String childType, Integer assetStatus,
        String manufacturer,
        String model, LocalDateTime acquisitionDate, Integer acquisitionPrice) {
        super(corporation, department, location, division, parentType, childType, assetStatus,
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

    public static GeneralAssetProperties of(String corporation, String department, String location,
        Integer division, String parentType, String childType,
        Integer assetStatus,
        String manufacturer, String model, LocalDateTime acquisitionDate,
        Integer acquisitionPrice) {
        return new GeneralAssetProperties(
            corporation, department, location, division, parentType,
            childType, assetStatus, manufacturer, model, acquisitionDate,
            acquisitionPrice);
    }


}
