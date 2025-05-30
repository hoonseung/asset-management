package com.sewon.assettype.controller;


import com.sewon.assettype.application.AssetTypeService;
import com.sewon.assettype.request.AssetTypeRegistrationRequest;
import com.sewon.assettype.response.AssetTypeListParentResponse;
import com.sewon.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("asset-types")
@RequiredArgsConstructor
@RestController
public class AssetTypeController {

    private final AssetTypeService assetTypeService;


    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerAssetType(
        @RequestBody AssetTypeRegistrationRequest request) {
        assetTypeService.registerAssetType(request.toAssetType(), request.parentId());
        return ResponseEntity.ok(ApiResponse.ok());
    }


    @GetMapping("/hierarchy")
    public ResponseEntity<ApiResponse<AssetTypeListParentResponse>> findAllAssetType() {
        AssetTypeListParentResponse response = AssetTypeListParentResponse.from(
            assetTypeService.findAllParentAssetType());
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
