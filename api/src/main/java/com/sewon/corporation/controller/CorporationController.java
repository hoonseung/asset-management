package com.sewon.corporation.controller;

import com.sewon.common.response.ApiResponse;
import com.sewon.corporation.application.CorporationService;
import com.sewon.corporation.request.CorporationRegistrationRequest;
import com.sewon.corporation.response.CorporationListResponse;
import com.sewon.corporation.response.CorporationOneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/corporations")
@RequiredArgsConstructor
@RestController
public class CorporationController {

    private final CorporationService corporationService;


    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerCorporation(
        @RequestBody CorporationRegistrationRequest request) {
        corporationService.registerCorporation(request.toCorporation());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CorporationOneResponse>> findByCorporationById(
        @PathVariable("id") Long id) {
        CorporationOneResponse response = CorporationOneResponse.from(
            corporationService.findByCorporationById(id));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CorporationListResponse>> findAllCorporation() {
        CorporationListResponse response = CorporationListResponse.from(
            corporationService.findAllCorporation());
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    //TODO account 도메인 작성 후 작업
    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<CorporationOneResponse>> findCorporationByAccount(
        @PathVariable("accountId") Long accountId) {
        return null;
    }

}
