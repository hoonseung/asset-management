package com.sewon.affiliation.controller;

import com.sewon.affiliation.application.AffiliationService;
import com.sewon.affiliation.request.AffiliationRegistrationRequest;
import com.sewon.affiliation.response.AffiliationListResponse;
import com.sewon.affiliation.response.AffiliationOneResponse;
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

@RequestMapping("/affiliations")
@RequiredArgsConstructor
@RestController
public class AffiliationController {

    private final AffiliationService affiliationService;


    @PostMapping
    public ResponseEntity<ApiResponse<AffiliationOneResponse>> registerAffiliation(
        @RequestBody @Valid AffiliationRegistrationRequest request) {
        AffiliationOneResponse response = AffiliationOneResponse.from(
            affiliationService.registerAffiliation(request.corporationId(), request.department())
        );
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PutMapping("/{corporationId}/{affiliationId}")
    public ResponseEntity<ApiResponse<Void>> updateAffiliation(
        @PathVariable("corporationId") Long corporationId,
        @PathVariable("affiliationId") Long affiliationId,
        @RequestParam(name = "department") String department
    ) {
        affiliationService.updateAffiliation(corporationId, affiliationId, department);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AffiliationListResponse>> findAffiliation() {
        AffiliationListResponse response = AffiliationListResponse.from(
            affiliationService.findAllAffiliationResult());
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{affiliationId}")
    public ResponseEntity<ApiResponse<Void>> deleteAffiliationById(
        @PathVariable("affiliationId") Long affiliationId) {
        affiliationService.deleteAffiliationById(affiliationId);
        return ResponseEntity.ok(ApiResponse.ok());
    }


}
