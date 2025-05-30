package com.sewon.assetlocation.controller;

import com.sewon.assetlocation.application.AssetLocationService;
import com.sewon.assetlocation.request.AssetLocationRegistrationRequest;
import com.sewon.assetlocation.response.AssetLocationListResponse;
import com.sewon.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/locations")
@RequiredArgsConstructor
@RestController
public class AssetLocationController {

    private final AssetLocationService assetLocationService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerAssetLocation(
        @RequestBody AssetLocationRegistrationRequest request) {
        assetLocationService.registerAssetLocation(request.location(), request.affiliationId());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/{affiliationId}")
    public ResponseEntity<ApiResponse<AssetLocationListResponse>> findAssetLocationByAffiliationId(
        @PathVariable("affiliationId") Long affiliationId) {
        AssetLocationListResponse response = AssetLocationListResponse.from(
            assetLocationService.findAllByAffiliationId(affiliationId));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
