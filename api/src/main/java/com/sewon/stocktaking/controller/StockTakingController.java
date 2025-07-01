package com.sewon.stocktaking.controller;

import com.sewon.asset.request.AssetSearchRequest;
import com.sewon.common.response.ApiResponse;
import com.sewon.security.model.auth.AuthUser;
import com.sewon.stocktaking.application.StockTakingService;
import com.sewon.stocktaking.dto.AssetStockingItemResult;
import com.sewon.stocktaking.dto.AssetStockingItemVerify;
import com.sewon.stocktaking.dto.StockTakingAsset;
import com.sewon.stocktaking.request.StockTakingRegistrationRequest;
import com.sewon.stocktaking.response.StockTakingAssetListSearchResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/stock-takings")
@RequiredArgsConstructor
@RestController
public class StockTakingController {

    private final StockTakingService stockTakingService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerStockTakingItems(
        @RequestBody StockTakingRegistrationRequest request,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        stockTakingService.registerStockTakingItems(request.barcodes(), request.realLocationId(),
            authUser.getId());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<AssetStockingItemVerify>> verifyStockTakingItems(
        @RequestBody StockTakingRegistrationRequest request
    ) {
        AssetStockingItemVerify assetStockingItemVerify = stockTakingService.verifyStockTakingItems(
            request.barcodes(), request.realLocationId());
        return ResponseEntity.ok(ApiResponse.ok(assetStockingItemVerify));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<StockTakingAssetListSearchResponse>> findAllAssetStockingItem(
        @ModelAttribute AssetSearchRequest request,
        @RequestParam(value = "check", defaultValue = "") String check
    ) {
        switch (check) {
            case "0" -> {
                List<AssetStockingItemResult> results = stockTakingService.findAllAssetStockingCompletedAsset(
                    request.toAssetSearchProperties());
                return ResponseEntity.ok(
                    ApiResponse.ok(StockTakingAssetListSearchResponse.completed(results))
                );
            }
            case "1" -> {
                List<AssetStockingItemResult> results = stockTakingService.findAllAssetStockingUnCompletedAsset(
                    request.toAssetSearchProperties());
                return ResponseEntity.ok(
                    ApiResponse.ok(StockTakingAssetListSearchResponse.uncompleted(results))
                );
            }
            default -> {
                List<StockTakingAsset> stockingAssets = stockTakingService.findAllWithAssetStockingAsset(
                    request.toAssetSearchProperties());
                return ResponseEntity.ok(
                    ApiResponse.ok(StockTakingAssetListSearchResponse.all(stockingAssets))
                );
            }
        }
    }
}
