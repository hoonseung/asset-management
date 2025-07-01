package com.sewon.stocktaking.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sewon.stocktaking.dto.AssetStockingItemResult;
import com.sewon.stocktaking.dto.StockTakingAsset;
import java.util.List;

public record StockTakingAssetListSearchResponse(
    @JsonInclude(Include.NON_NULL)
    Integer completedCount,
    @JsonInclude(Include.NON_EMPTY)
    List<StockTakingAssetSearchResponse> competedList,

    @JsonInclude(Include.NON_NULL)
    Integer uncompletedCount,
    @JsonInclude(Include.NON_EMPTY)
    List<StockTakingAssetSearchResponse> uncompetedList
) {


    public static StockTakingAssetListSearchResponse completed(
        List<AssetStockingItemResult> assets) {
        List<StockTakingAssetSearchResponse> list = assets.stream()
            .map(asset -> StockTakingAssetSearchResponse.of(asset, true))
            .toList();
        return new StockTakingAssetListSearchResponse(
            list.size(), list
            , 0, List.of()
        );
    }

    public static StockTakingAssetListSearchResponse uncompleted(
        List<AssetStockingItemResult> results) {
        List<StockTakingAssetSearchResponse> list = results.stream()
            .map(asset -> StockTakingAssetSearchResponse.of(asset, false))
            .toList();
        return new StockTakingAssetListSearchResponse(0, List.of(),
            list.size(), list
        );
    }

    public static StockTakingAssetListSearchResponse all(List<StockTakingAsset> stockTakingAssets) {
        List<StockTakingAssetSearchResponse> completeList = stockTakingAssets.stream()
            .filter(StockTakingAsset::isStockTaking)
            .map(it -> StockTakingAssetSearchResponse.of(it.result(), true))
            .toList();
        List<StockTakingAssetSearchResponse> uncompleteList = stockTakingAssets.stream()
            .filter(StockTakingAsset::isNotStockTaking)
            .map(it -> StockTakingAssetSearchResponse.of(it.result(), false))
            .toList();

        return new StockTakingAssetListSearchResponse(
            completeList.size(), completeList, uncompleteList.size(), uncompleteList
        );
    }
}
