package com.sewon.asset.controller;

import com.sewon.asset.application.AssetService;
import com.sewon.asset.model.Asset;
import com.sewon.asset.request.AssetDeleteRequest;
import com.sewon.asset.request.AssetListRegistrationRequest;
import com.sewon.asset.request.AssetRegistrationRequest;
import com.sewon.asset.request.AssetSearchRequest;
import com.sewon.asset.request.AssetTransferRequest;
import com.sewon.asset.request.AssetUpdateRequest;
import com.sewon.asset.request.electronic.ElectronicAssetListRegistrationRequest;
import com.sewon.asset.request.electronic.ElectronicAssetRegistrationRequest;
import com.sewon.asset.request.electronic.ElectronicAssetUpdateRequest;
import com.sewon.asset.response.AssetEnableRentalResponse;
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
    public ResponseEntity<ApiResponse<Void>> updateAsset(
        @RequestBody AssetUpdateRequest request,
        @RequestParam("barcode") String barcode) {
        assetService.updateAsset(request.toGeneralAssetProperties(), barcode);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/electronic")
    public ResponseEntity<ApiResponse<Void>> updateElectronicAsset(
        @RequestBody ElectronicAssetUpdateRequest request,
        @RequestParam("barcode") String barcode) {
        assetService.updateAsset(request.toElectronicAssetProperties(), barcode);
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

    @GetMapping("/barcode")
    public ResponseEntity<ApiResponse<AssetOneResponse>> findAssetByBarcode(
        @RequestParam("value") String value) {
        AssetOneResponse response = AssetOneResponse.from(assetService.findAssetByBarcode(value));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllByCondition(
        @ModelAttribute AssetSearchRequest request
    ) {
        AssetListResponse listResponse = AssetListResponse.fromAllAsset(
            assetService.findAllByCondition(
                request.toAssetSearchProperties()));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize())
        );
    }

    @GetMapping("/paged/rental/enabled")
    public ResponseEntity<ApiPagingResponse<List<AssetEnableRentalResponse>>> findAllRentalEnableAssetByCondition(
        @ModelAttribute AssetSearchRequest request
    ) {
        List<AssetEnableRentalResponse> response = assetService.findAllRentalEnableAssetByCondition(
                request.toAssetSearchProperties()).stream().map(AssetEnableRentalResponse::from)
            .toList();
        return ResponseEntity.ok(
            ApiPagingResponse.ok(response, response.size())
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAllAssetById(
        @RequestBody AssetDeleteRequest request) {
        assetService.deleteAllAssetById(request.ids());
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
