package com.sewon.stocktaking.application;

import static com.sewon.stocktaking.exception.StockTakingErrorCode.STOCK_TAKING_ALREADY_LOCATION;

import com.sewon.account.application.AccountService;
import com.sewon.account.model.Account;
import com.sewon.asset.model.Asset;
import com.sewon.assetlocation.application.AssetLocationService;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.barcode.application.BarcodeService;
import com.sewon.common.exception.DomainException;
import com.sewon.stocktaking.model.AssetStockTaking;
import com.sewon.stocktaking.model.AssetStockTakingItem;
import com.sewon.stocktaking.repository.AssetStockTakingItemRepository;
import com.sewon.stocktaking.repository.AssetStockTakingRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StockTakingService {

    private final AssetStockTakingItemRepository assetStockTakingItemRepository;
    private final AssetStockTakingRepository assetStockTakingRepository;
    private final BarcodeService barcodeService;
    private final AssetLocationService assetLocationService;
    private final AccountService accountService;

    @Transactional
    public void registerStockTakingItems(List<String> barcodes, String realLocation,
        LocalDate auditingDate, Long id) {
        AssetLocation assetLocation = assetLocationService.findAssetLocationByLocation(
            realLocation);
        Account account = accountService.findAccountById(id);

        assetStockTakingRepository.findByLocationAndAuditingDate(assetLocation, auditingDate)
            .ifPresent(item -> new DomainException(STOCK_TAKING_ALREADY_LOCATION));

        List<AssetStockTakingItem> items = new ArrayList<>();
        AssetStockTaking assetStockTaking = assetStockTakingRepository.save(
            AssetStockTaking.of(auditingDate, account,
                assetLocation));
        barcodes.forEach(value ->
        {
            Asset asset = barcodeService.findAssetByValue(value);

            items.add(AssetStockTakingItem.of(realLocation, asset, assetStockTaking, assetLocation,
                false));
        });

        assetStockTakingItemRepository.saveAll(items);
    }
}
