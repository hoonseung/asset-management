package com.sewon.asset.dto;

import com.sewon.account.model.Account;
import com.sewon.asset.model.Asset;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assettype.model.AssetType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AssetProperties {

    protected Long locationId;
    protected Integer division;
    protected Long parentTypeId;
    protected Long childTypeId;
    protected Integer assetStatus;
    protected String manufacturer;
    protected String model;
    protected LocalDateTime acquisitionDate;
    protected Integer acquisitionPrice;


    public abstract Asset toAsset(AssetType assetType, Account account,
        AssetLocation assetLocation);

    public abstract Asset updateAsset(Asset asset, AssetLocation assetLocation,
        AssetType assetType);
}
