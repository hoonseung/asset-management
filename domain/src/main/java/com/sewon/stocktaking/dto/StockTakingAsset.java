package com.sewon.stocktaking.dto;

public record StockTakingAsset(
    AssetStockingItemResult result,
    boolean isCheck
) {

    public static StockTakingAsset of(AssetStockingItemResult result, boolean isCheck) {
        return new StockTakingAsset(result, isCheck);
    }

    public boolean isStockTaking() {
        return isCheck;
    }

    public boolean isNotStockTaking() {
        return !isCheck;
    }
}
