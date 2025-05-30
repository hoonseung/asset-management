package com.sewon.stocktaking;

import com.sewon.common.response.ApiResponse;
import com.sewon.stocktaking.application.StockTakingService;
import com.sewon.stocktaking.request.StockTakingRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/stock-takings")
@RequiredArgsConstructor
@RestController
public class StockTakingController {

    private final StockTakingService stockTakingService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerStockTakingItems(
        @RequestBody StockTakingRegistrationRequest request
    ) {
        stockTakingService.registerStockTakingItems(request.barcodes(), request.realLocation(),
            request.auditingDate(),
            1L);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
