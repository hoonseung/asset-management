package com.sewon.rental.controller;

import com.sewon.common.response.ApiResponse;
import com.sewon.rental.application.AssetRentalService;
import com.sewon.rental.model.AssetRental;
import com.sewon.rental.request.RentalApproveRequest;
import com.sewon.rental.request.RentalRequestRequest;
import com.sewon.rental.response.RentalListResponse;
import com.sewon.security.model.auth.AuthUser;
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
        @RequestBody RentalRequestRequest request,
        @AuthenticationPrincipal AuthUser authUser) {
        assetRentalService.requestAssetRental(
            request.assetId(), request.locationId(), request.fromDate(), request.toDate(),
            authUser.getId());
        return ResponseEntity.ok(ApiResponse.ok());
    }


    @PostMapping("/approve")
    public ResponseEntity<ApiResponse<Void>> approveAssetRental(
        @RequestBody RentalApproveRequest request) {
        assetRentalService.approveAssetRental(request.rentalId());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<RentalListResponse>> findAllAssetRentalRequestingByDepartment(
        @RequestParam("department") String department
    ) {
        RentalListResponse response = RentalListResponse.from(
            assetRentalService.findAllAssetRentalRequestingByDepartment(department));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }


    @GetMapping("/request/user")
    public ResponseEntity<ApiResponse<RentalListResponse>> findAllMyRequestingAssetRentalByAccountName(
        @AuthenticationPrincipal AuthUser authUser) {
        RentalListResponse response = RentalListResponse.from(
            assetRentalService.findAllMyRequestingAssetRentalByAccountName(authUser.getUsername()));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }


    @GetMapping("/user")
    public ResponseEntity<ApiResponse<RentalListResponse>> findAllAssetRentalByAccountName(
        @AuthenticationPrincipal AuthUser authUser) {
        List<AssetRental> assetRentals = assetRentalService.findAllAssetRentedByAccountName(
            "admin");
        assetRentals.addAll(
            assetRentalService.findAllAssetRentExpireByAccountName(authUser.getUsername()));
        RentalListResponse response = RentalListResponse.from(assetRentals);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssetRentalById(
        @PathVariable("id") Long id) {
        assetRentalService.deleteAssetRentalById(id);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
