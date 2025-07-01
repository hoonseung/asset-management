package com.sewon.stocktaking.dto;

import java.util.List;

public record AssetStockingItemVerify(
    List<ItemVerify> matchItem,
    List<ItemVerify> unmatchItem,
    List<ItemVerify> disableItem
) {

    public record ItemVerify(
        String barcode,
        String assetLocation,
        String stockTakingLocation,
        String status
    ) {

    }
}
