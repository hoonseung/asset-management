package com.sewon.asset.controller;

import com.sewon.asset.application.AssetService;
import com.sewon.asset.model.Asset;
import com.sewon.asset.request.AssetListRegistrationRequest;
import com.sewon.asset.request.AssetRegistrationRequest;
import com.sewon.asset.request.AssetSearchRequest;
import com.sewon.asset.request.AssetTransferRequest;
import com.sewon.asset.request.AssetUpdateRequest;
import com.sewon.asset.request.electronic.ElectronicAssetListRegistrationRequest;
import com.sewon.asset.request.electronic.ElectronicAssetRegistrationRequest;
import com.sewon.asset.response.AssetListResponse;
import com.sewon.asset.response.AssetOneResponse;
import com.sewon.common.response.ApiPagingResponse;
import com.sewon.common.response.ApiResponse;
import com.sewon.security.model.auth.AuthUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/assets")
@RequiredArgsConstructor
@RestController
public class AssetController {

    private final AssetService assetService;


    @PostMapping
    public ResponseEntity<ApiResponse<String>> registerAsset(
        @RequestBody AssetRegistrationRequest request,
        @AuthenticationPrincipal AuthUser authUser) {
        Asset asset = assetService.registerAsset(request.toGeneralAssetProperties(),
            authUser.getId());
        return ResponseEntity.ok(ApiResponse.ok(asset.getBarcodeValue()));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<String>> updateAsset(
        @RequestBody AssetUpdateRequest request,
        @RequestParam("barcode") String barcode) {
        assetService.updateAsset(request.toGeneralAssetProperties(), barcode);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/electronic")
    public ResponseEntity<ApiResponse<String>> registerElectronicAsset(
        @RequestBody ElectronicAssetRegistrationRequest request,
        @AuthenticationPrincipal AuthUser authUser) {
        Asset asset = assetService.registerAsset(request.toElectronicAssetProperties(),
            authUser.getId());
        return ResponseEntity.ok(ApiResponse.ok(asset.getBarcodeValue()));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<String>>> registerAssetList(
        @RequestBody AssetListRegistrationRequest request,
        @AuthenticationPrincipal AuthUser authUser) {
        List<Asset> assets = assetService.registerAssetList(request.toGeneralAssetPropertiesList(),
            authUser.getId());
        return ResponseEntity.ok(ApiResponse.ok(assets.stream()
            .map(Asset::getBarcodeValue)
            .toList()));
    }

    @PostMapping("/electronic/bulk")
    public ResponseEntity<ApiResponse<List<String>>> registerElectronicAssetList(
        @RequestBody ElectronicAssetListRegistrationRequest request,
        @AuthenticationPrincipal AuthUser authUser) {
        List<Asset> assets = assetService.registerAssetList(request.toGeneralAssetPropertiesList(),
            authUser.getId());
        return ResponseEntity.ok(ApiResponse.ok(assets.stream()
            .map(Asset::getBarcodeValue)
            .toList()));
    }

    @PostMapping("/transmission")
    public ResponseEntity<ApiResponse<Void>> transferAsset(
        @RequestBody AssetTransferRequest request,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        assetService.transferAsset(request.assetId(), request.fromLocationId()
            , request.toLocationId(), authUser.getId());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetOneResponse>> findAssetById(
        @PathVariable("id") Long id) {
        AssetOneResponse response = AssetOneResponse.from(assetService.findAssetResultById(id));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/barcode")
    public ResponseEntity<ApiResponse<AssetOneResponse>> findAssetByBarcode(
        @RequestParam("value") String value) {
        AssetOneResponse response = AssetOneResponse.from(assetService.findAssetByBarcode(value));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AssetListResponse>> findAllAsset() {
        AssetListResponse listResponse = AssetListResponse.from(assetService.findAllAsset());
        return ResponseEntity.ok(ApiResponse.ok(listResponse));
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllByCondition(
        @ModelAttribute AssetSearchRequest request
    ) {
        AssetListResponse listResponse = AssetListResponse.from(
            assetService.findAllByCondition(
                request.toAssetSearchProperties()));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize())
        );
    }

    @GetMapping("/paged/rental/enabled")
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllRentalEnableAssetByCondition(
        @ModelAttribute AssetSearchRequest request
    ) {
        AssetListResponse listResponse = AssetListResponse.from(
            assetService.findAllRentalEnableAssetByCondition(
                request.toAssetSearchProperties()));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssetById(
        @PathVariable("id") Long id) {
        assetService.deleteAssetById(id);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
