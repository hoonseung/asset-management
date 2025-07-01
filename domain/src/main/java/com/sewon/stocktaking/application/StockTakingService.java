package com.sewon.stocktaking.application;

import static com.sewon.stocktaking.constant.AssetCheckingStatus.DISABLE;
import static com.sewon.stocktaking.constant.AssetCheckingStatus.MATCH;
import static com.sewon.stocktaking.constant.AssetCheckingStatus.MISMATCH;
import static com.sewon.stocktaking.exception.StockTakingErrorCode.STOCK_TAKING_EXPIRATION;
import static java.time.LocalDateTime.now;

import com.sewon.account.application.AccountService;
import com.sewon.account.model.Account;
import com.sewon.asset.dto.AssetResult;
import com.sewon.asset.dto.AssetSearchProperties;
import com.sewon.asset.model.Asset;
import com.sewon.asset.repository.AssetSearchRepository;
import com.sewon.assetlocation.application.AssetLocationService;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assettype.model.AssetType;
import com.sewon.barcode.application.BarcodeService;
import com.sewon.common.exception.DomainException;
import com.sewon.inbound.application.AssetInboundService;
import com.sewon.inbound.constant.InboundType;
import com.sewon.inbound.model.AssetInbound;
import com.sewon.stocktaking.dto.AssetStockingItemResult;
import com.sewon.stocktaking.dto.AssetStockingItemVerify;
import com.sewon.stocktaking.dto.AssetStockingItemVerify.ItemVerify;
import com.sewon.stocktaking.dto.StockTakingAsset;
import com.sewon.stocktaking.model.AssetStockTaking;
import com.sewon.stocktaking.model.AssetStockTakingItem;
import com.sewon.stocktaking.repository.AssetStockTakingItemRepository;
import com.sewon.stocktaking.repository.AssetStockTakingRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
    private final AssetSearchRepository assetSearchRepository;
    private final BarcodeService barcodeService;
    private final AssetLocationService assetLocationService;
    private final AccountService accountService;
    private final AssetInboundService assetInboundService;

    @Transactional
    public void registerStockTakingItems(List<String> barcodes, Long realLocationId,
        Long accountId) {
        Optional<AssetStockTaking> stockTaking = assetStockTakingRepository.findByAssetLocationId(
            realLocationId);
        AssetStockTaking assetStockTaking;
        AssetLocation assetLocation;
        Account account;

        if (stockTaking.isPresent()) {
            assetStockTaking = stockTaking.get();
            // 실사 시작일 1일 + 4일 보다  (총 5일) 보다 이후 인지 확인
            if (LocalDate.now().isAfter(assetStockTaking.getStartingDate().plusDays(4))) {
                assetStockTaking.setAuditingDate(assetStockTaking.getStartingDate().plusDays(5));
                throw new DomainException(STOCK_TAKING_EXPIRATION);
            } else {
                assetLocation = assetStockTaking.getAssetLocation();
                account = accountService.findAccountById(accountId);
            }
        } else {
            assetLocation = assetLocationService.findAssetLocationById(
                realLocationId);
            account = accountService.findAccountById(accountId);
            assetStockTaking = assetStockTakingRepository.save(
                AssetStockTaking.of(LocalDate.now(), account,
                    assetLocation));
        }
        List<AssetStockTakingItem> items = new ArrayList<>();

        barcodes.forEach(value ->
        {
            Asset asset = barcodeService.findAssetByValue(value);
            AssetStockTakingItem item = AssetStockTakingItem.of(
                assetLocation, asset, assetStockTaking);
            if (Boolean.TRUE.equals(item.getIsChanged())) {
                asset.setAssetLocation(assetLocation);
                assetInboundService.registerAssetInbound(
                    AssetInbound.of(InboundType.STOCK_TAKING, now(),
                        account, asset, assetLocation)
                );

            }
            items.add(item);
        });

        assetStockTakingItemRepository.saveAll(items);
    }

    @Transactional
    public AssetStockingItemVerify verifyStockTakingItems(List<String> barcodes,
        Long realLocationId) {
        AssetLocation assetLocation = assetLocationService.findAssetLocationById(
            realLocationId);

        List<ItemVerify> match = new ArrayList<>();
        List<ItemVerify> unmatch = new ArrayList<>();
        List<ItemVerify> diable = new ArrayList<>();

        barcodes.forEach(value ->
        {
            Asset asset = barcodeService.findAssetByValue(value);
            if (AssetStockTakingItem.diable(
                AssetStockTakingItem.getAssetCheckingStatus(asset, assetLocation))) {
                diable.add(new ItemVerify(value, asset.getLocation(),
                    assetLocation.getLocation(), DISABLE.name()));
            } else if (AssetStockTakingItem.change(
                AssetStockTakingItem.getAssetCheckingStatus(asset, assetLocation))) {
                match.add(new ItemVerify(value, asset.getLocation(),
                    assetLocation.getLocation(), MISMATCH.name()));
            } else {
                unmatch.add(new ItemVerify(value, asset.getLocation(),
                    assetLocation.getLocation(), MATCH.name()));
            }

        });
        return new AssetStockingItemVerify(match, unmatch, diable);
    }


    public List<StockTakingAsset> findAllWithAssetStockingAsset(AssetSearchProperties properties) {
        // 해당 위치 자산 전부 조회
        List<AssetResult> allAsset = assetSearchRepository.searchAssets(
            properties.toWithOutBetween());
        // 실사 조회
        List<AssetStockTaking> assetStockTaking = assetStockTakingRepository.findByLocationIdAndBetweenStartingDate(
            properties.locationId(),
            properties.after(), properties.before());

        List<Asset> stockAssets = getStockTakingAsset(properties, assetStockTaking);
        List<StockTakingAsset> stockTakingAssets = stockAssets.stream()
            .map(it -> StockTakingAsset.of(AssetStockingItemResult.from(it), true))
            .toList();

        List<StockTakingAsset> assets = new ArrayList<>(stockTakingAssets);
        // 해당 위치 자산이랑 실사 자산이랑 일치 하지 않는 자산은 실사 미완료
        allAsset.stream()
            .filter(asset -> stockAssets.stream()
                .allMatch(stockAsset -> !stockAsset.getId().equals(asset.id())
                    && asset.assetTypeId().equals(stockAsset.getAssetTypeId()))
            ).map(it -> StockTakingAsset.of(AssetStockingItemResult.from(it), false))
            .forEach(assets::add);

        return assets;
    }

    public List<AssetStockingItemResult> findAllAssetStockingCompletedAsset(
        AssetSearchProperties properties) {
        // 실사 조회
        List<AssetStockTaking> assetStockTaking = assetStockTakingRepository.findByLocationIdAndBetweenStartingDate(
            properties.locationId(),
            properties.after(), properties.before());

        List<Asset> stockAssets = getStockTakingAsset(properties, assetStockTaking);
        return new ArrayList<>(stockAssets).stream().map(AssetStockingItemResult::from).toList();
    }

    public List<AssetStockingItemResult> findAllAssetStockingUnCompletedAsset(
        AssetSearchProperties properties) {
        List<AssetResult> assets = new ArrayList<>();
        // 실사 조회
        List<AssetResult> allAsset = assetSearchRepository.searchAssets(
            properties.toWithOutBetween());
        List<AssetStockTaking> assetStockTaking = assetStockTakingRepository.findByLocationIdAndBetweenStartingDate(
            properties.locationId(),
            properties.after(), properties.before());

        List<Asset> stockAssets = getStockTakingAsset(properties, assetStockTaking);
        allAsset.stream()
            .filter(asset -> stockAssets.stream()
                .allMatch(stockAsset -> !stockAsset.getId().equals(asset.id())
                    && asset.assetTypeId().equals(stockAsset.getAssetTypeId()))
            ).forEach(assets::add);
        return assets.stream()
            .map(AssetStockingItemResult::from)
            .toList();
    }


    private List<Asset> getStockTakingAsset(AssetSearchProperties properties,
        List<AssetStockTaking> assetStockTakings) {
        // 실사한 아이템 조회

        List<AssetStockTakingItem> stockTakingItems = assetStockTakings.stream()
            .map(it -> assetStockTakingItemRepository.findAllByStockTakingId(it.getId()))
            .flatMap(Collection::stream).toList();

        // 실사 자산 추가
        return stockTakingItems.stream()
            .map(AssetStockTakingItem::getAsset)
            .filter(asset -> eqParentAndChildeType(asset.getAssetType(),
                properties.parentTypeId(), properties.childTypeId()))
            .toList();
    }


    private Boolean eqParentAndChildeType(AssetType assetType, Long parentTypeId,
        Long childTypeId) {
        if (parentTypeId != null && childTypeId != null) {
            if (assetType.getAssetType() != null) {
                return assetType.getId().equals(childTypeId)
                    && (assetType.getAssetType().getId().equals(parentTypeId));
            } else {
                return assetType.getId().equals(parentTypeId);
            }
        } else if (parentTypeId != null) {
            if (assetType.getAssetType() == null) {
                return assetType.getId().equals(parentTypeId);
            }
        } else if (childTypeId != null && assetType.getAssetType() != null) {
            return assetType.getId().equals(childTypeId);
        }
        // parent, child id가 없으면 true를 반환하여 모두 반환
        return true;
    }
}





