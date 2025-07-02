package com.sewon.metrics;

import com.sewon.common.response.ApiResponse;
import com.sewon.metrics.application.AssetMetricsService;
import com.sewon.metrics.dto.AssetMetrics;
import com.sewon.metrics.dto.AssetStockTakingMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/metrics")
@RequiredArgsConstructor
@RestController
public class AssetMetricsController {

    private final AssetMetricsService assetMetricsService;

    @GetMapping("/assets/{affiliationId}")
    public ResponseEntity<ApiResponse<AssetMetrics>> getAssetMetrics(
        @PathVariable("affiliationId") Long affiliationId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
            assetMetricsService.getAssetMetrics(affiliationId)
        ));
    }

    @GetMapping("/stock-takings/{affiliationId}")
    public ResponseEntity<ApiResponse<AssetStockTakingMetrics>> getAssetStockTakingMetrics(
        @PathVariable("affiliationId") Long affiliationId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
            assetMetricsService.getAssetStockTakingMetrics(affiliationId)
        ));
    }


}
