package com.sewon.asset.controller;

import com.sewon.asset.application.AssetService;
import com.sewon.asset.model.Asset;
import com.sewon.asset.request.AssetListRegistrationRequest;
import com.sewon.asset.request.AssetRegistrationRequest;
import com.sewon.asset.request.electronic.ElectronicAssetListRegistrationRequest;
import com.sewon.asset.request.electronic.ElectronicAssetRegistrationRequest;
import com.sewon.asset.response.AssetListResponse;
import com.sewon.asset.response.AssetOneResponse;
import com.sewon.common.response.ApiPagingResponse;
import com.sewon.common.response.ApiResponse;
import com.sewon.security.model.auth.AuthUser;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        @Valid @RequestBody AssetRegistrationRequest request,
        @AuthenticationPrincipal AuthUser authUser) {
        Asset asset = assetService.registerAsset(request.toGeneralAssetProperties(),
            authUser.getId());
        return ResponseEntity.ok(ApiResponse.ok(asset.getBarcodeValue()));
    }

    @PostMapping("/electronic")
    public ResponseEntity<ApiResponse<String>> registerElectronicAsset(
        @Valid @RequestBody ElectronicAssetRegistrationRequest request,
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetOneResponse>> findAssetById(
        @PathVariable("id") Long id) {
        AssetOneResponse response = AssetOneResponse.from(assetService.findAssetById(id));
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
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllAsset(
        @RequestParam(name = "size", defaultValue = "30") int size) {
        AssetListResponse listResponse = AssetListResponse.from(assetService.findAllAsset(size));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize()));
    }

    @GetMapping("/paged/by-location")
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllAssetByLocation(
        @RequestParam("location") String location,
        @RequestParam(name = "size", defaultValue = "30") int size) {
        AssetListResponse listResponse = AssetListResponse.from(
            assetService.findAllAssetByLocation(location, size));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize()));
    }

    @GetMapping("/paged/by-location-and-childType")
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllAssetByLocationAndChildType(
        @RequestParam("location") String location,
        @RequestParam("type") String type,
        @RequestParam(name = "size", defaultValue = "30") int size
    ) {
        AssetListResponse listResponse = AssetListResponse.from(
            assetService.findAllByLocationAndChildType(location, type, size));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize()));
    }

    @GetMapping("/paged/by-location-and-childType-between")
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllAssetByLocationAndChildTypeBetween(
        @RequestParam("location") String location,
        @RequestParam("type") String type,
        @RequestParam("after") LocalDate after,
        @RequestParam("before") LocalDate before,
        @RequestParam(name = "size", defaultValue = "30") int size
    ) {
        AssetListResponse listResponse = AssetListResponse.from(
            assetService.findAllByLocationAndChildTypeBetween(location, type, after, before, size));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize()));
    }

    @GetMapping("/paged/by-location-and-parentType")
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllAssetByLocationAndParentType(
        @RequestParam("location") String location,
        @RequestParam("type") String type,
        @RequestParam(name = "size", defaultValue = "30") int size
    ) {
        AssetListResponse listResponse = AssetListResponse.from(
            assetService.findAllByLocationAndParentType(location, type, size));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize()));
    }

    @GetMapping("/paged/by-location-and-parentType-between")
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllAssetByLocationAndParentTypeBetween(
        @RequestParam("location") String location,
        @RequestParam("type") String type,
        @RequestParam("after") LocalDate after,
        @RequestParam("before") LocalDate before,
        @RequestParam(name = "size", defaultValue = "30") int size
    ) {
        AssetListResponse listResponse = AssetListResponse.from(
            assetService.findAllByLocationAndParentTypeBetween(location, type, after, before,
                size));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize()));
    }

    @GetMapping("/paged/between")
    public ResponseEntity<ApiPagingResponse<AssetListResponse>> findAllBetween(
        @RequestParam("after") LocalDate after,
        @RequestParam("before") LocalDate before,
        @RequestParam(name = "size", defaultValue = "30") int size
    ) {
        AssetListResponse listResponse = AssetListResponse.from(
            assetService.findAllBetween(after, before, size));
        return ResponseEntity.ok(
            ApiPagingResponse.ok(listResponse, listResponse.listResponseSize()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssetById(
        @PathVariable("id") Long id) {
        assetService.deleteAssetById(id);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
