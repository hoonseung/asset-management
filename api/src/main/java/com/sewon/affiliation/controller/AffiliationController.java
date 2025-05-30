package com.sewon.affiliation.controller;

import com.sewon.affiliation.application.AffiliationService;
import com.sewon.affiliation.request.AffiliationRegistrationRequest;
import com.sewon.affiliation.response.AffiliationListResponse;
import com.sewon.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/affiliations")
@RequiredArgsConstructor
@RestController
public class AffiliationController {

    private final AffiliationService affiliationService;


    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerAffiliation(
        @RequestBody AffiliationRegistrationRequest request) {
        affiliationService.registerAffiliation(request.corporationId(), request.department());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AffiliationListResponse>> findAffiliation() {
        AffiliationListResponse response = AffiliationListResponse.from(
            affiliationService.findAllAffiliation());
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAffiliationById(
        @PathVariable("id") Long id) {
        affiliationService.deleteAffiliationById(id);
        return ResponseEntity.ok(ApiResponse.ok());
    }


}
