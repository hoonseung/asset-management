package com.sewon.stocktaking.response;

import com.sewon.stocktaking.dto.AssetStockingItemResult;
import java.time.LocalDate;

public record StockTakingAssetSearchResponse(
    Long id,
    String barcode,
    String corporation,
    String department,
    String location,
    String division,
    String parentCategory,
    String childCategory,
    String status,
    String manufacturer,
    String model,
    LocalDate acquisitionDate,
    Integer acquisitionPrice,
    String registerName,
    boolean isStockTaking
) {

    public static StockTakingAssetSearchResponse of(AssetStockingItemResult result,
        boolean isStockTaking) {
        return new StockTakingAssetSearchResponse(
            result.id(),
            result.barcode(),
            result.corporation(),
            result.department(),
            result.location(),
            result.division(),
            result.parentCategory(),
            result.childCategory(),
            result.status(),
            result.manufacturer(),
            result.model(),
            result.acquisitionDate(),
            result.acquisitionPrice(),
            result.registerName(),
            isStockTaking
        );
    }
}
