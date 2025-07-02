package com.sewon.rental.controller;

import com.sewon.common.response.ApiResponse;
import com.sewon.rental.application.AssetRentalService;
import com.sewon.rental.request.ReturnApproveRequest;
import com.sewon.rental.request.ReturnRequest;
import com.sewon.rental.response.RentalListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/return")
@RestController()
public class AssetReturnController {

    private final AssetRentalService assetRentalService;


    @PostMapping("/request")
    public ResponseEntity<ApiResponse<Void>> requestAssetReturn(
        @RequestBody ReturnRequest request) {
        assetRentalService.requestAssetReturn(request.ids());
        return ResponseEntity.ok(ApiResponse.ok());
    }


    @PostMapping("/approve")
    public ResponseEntity<ApiResponse<Void>> approveAssetRental(
        @RequestBody @Valid ReturnApproveRequest request) {
        assetRentalService.approveAssetReturn(request.ids());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/request/mine")
    public ResponseEntity<ApiResponse<RentalListResponse>> findAllRequestingAssetReturnByAffiliation(
        @RequestParam("affiliationId") Long affiliationId
    ) {
        RentalListResponse response = RentalListResponse.from(
            assetRentalService.findAllAssetReturnRequestingMyAffiliation(affiliationId));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/request/others")
    public ResponseEntity<ApiResponse<RentalListResponse>> findAllAssetReturnRequestingByOtherAffiliation(
        @RequestParam("affiliationId") Long affiliationId
    ) {
        RentalListResponse response = RentalListResponse.from(
            assetRentalService.findAllAssetReturnRequestingByOtherAffiliation(affiliationId));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

}
