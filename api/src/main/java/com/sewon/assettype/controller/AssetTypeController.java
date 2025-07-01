package com.sewon.assettype.controller;


import com.sewon.assettype.application.AssetTypeService;
import com.sewon.assettype.request.AssetTypeRegistrationRequest;
import com.sewon.assettype.response.AssetTypeListParentResponse;
import com.sewon.common.response.ApiResponse;
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

    @PutMapping("/{typeId}")
    public ResponseEntity<ApiResponse<Void>> updateAssetLocation(
        @PathVariable("typeId") Long typeId,
        @RequestParam("type") String type) {
        assetTypeService.updateAssetType(typeId, type);
        return ResponseEntity.ok(ApiResponse.ok());
    }


    @GetMapping("/hierarchy")
    public ResponseEntity<ApiResponse<AssetTypeListParentResponse>> findAllAssetType() {
        AssetTypeListParentResponse response = AssetTypeListParentResponse.from(
            assetTypeService.findAllParentAssetType());
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<ApiResponse<Void>> deleteAssetType(
        @PathVariable("typeId") Long typeId
    ) {
        assetTypeService.deleteAssetTypeById(typeId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
