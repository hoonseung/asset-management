package com.sewon.rental.controller;

import com.sewon.common.response.ApiResponse;
import com.sewon.rental.application.AssetRentalService;
import com.sewon.rental.dto.AssetRentalResult;
import com.sewon.rental.request.RentalApproveRequest;
import com.sewon.rental.request.RentalRequest;
import com.sewon.rental.response.RentalListResponse;
import com.sewon.security.model.auth.AuthUser;
import jakarta.validation.Valid;
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

@RequiredArgsConstructor
@RequestMapping("/rental")
@RestController
public class AssetRentalController {

    private final AssetRentalService assetRentalService;


    @PostMapping("/request")
    public ResponseEntity<ApiResponse<Void>> requestAssetRental(
        @RequestBody RentalRequest request,
        @AuthenticationPrincipal AuthUser authUser) {
        assetRentalService.requestAssetRental(
            request.assetId(), request.locationId(), request.fromDate(), request.toDate(),
            authUser.getId());
        return ResponseEntity.ok(ApiResponse.ok());
    }


    @PostMapping("/approve")
    public ResponseEntity<ApiResponse<Void>> approveAssetRental(
        @RequestBody @Valid RentalApproveRequest request) {
        assetRentalService.approveAssetRental(request.ids());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/request/others")
    public ResponseEntity<ApiResponse<RentalListResponse>> findAllAssetRentalRequestingByOtherAffiliation(
        @RequestParam("affiliationId") Long affiliationId
    ) {
        RentalListResponse response = RentalListResponse.from(
            assetRentalService.findAllRequestingAssetRentalByAssetAffiliation(affiliationId));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/request/mine")
    public ResponseEntity<ApiResponse<RentalListResponse>> findAllRequestingAssetRentalByAffiliation(
        @RequestParam("affiliationId") Long affiliationId
    ) {
        RentalListResponse response = RentalListResponse.from(
            assetRentalService.findAllRequestingAssetRentalMyAffiliation(affiliationId));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<RentalListResponse>> findAllAssetRentalByDepartment(
        @RequestParam("affiliationId") Long affiliationId) {
        List<AssetRentalResult> assetRentals = assetRentalService.findAllAssetRentedMyAffiliation(
            affiliationId);
        RentalListResponse response = RentalListResponse.fromIncludeExpire(assetRentals);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssetRentalById(
        @PathVariable("id") Long id) {
        assetRentalService.deleteAssetRentalById(id);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
