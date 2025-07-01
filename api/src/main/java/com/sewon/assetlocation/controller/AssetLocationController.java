package com.sewon.assetlocation.controller;

import com.sewon.assetlocation.application.AssetLocationService;
import com.sewon.assetlocation.request.AssetLocationRegistrationRequest;
import com.sewon.assetlocation.response.AssetLocationListResponse;
import com.sewon.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/locations")
@RequiredArgsConstructor
@RestController
public class AssetLocationController {

    private final AssetLocationService assetLocationService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerAssetLocation(
        @RequestBody @Valid AssetLocationRegistrationRequest request) {
        assetLocationService.registerAssetLocation(request.location(), request.affiliationId());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/{affiliationId}/{locationId}")
    public ResponseEntity<ApiResponse<Void>> updateAssetLocation(
        @PathVariable("affiliationId") Long affiliationId,
        @PathVariable("locationId") Long locationId,
        @RequestParam("location") String location) {
        assetLocationService.updateAssetLocation(affiliationId, locationId, location);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/affiliation/{id}")
    public ResponseEntity<ApiResponse<AssetLocationListResponse>> findAssetLocationByAffiliationId(
        @PathVariable("id") Long id) {
        AssetLocationListResponse response = AssetLocationListResponse.from(
            assetLocationService.findAllByAffiliationId(id));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<ApiResponse<Void>> deleteAssetLocationById(
        @PathVariable("locationId") Long locationId
    ) {
        assetLocationService.deleteAssetLocationById(locationId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
