package com.sewon.asset.application;

import static com.sewon.asset.exception.AssetErrorCode.ASSET_NOT_FOUND;
import static java.time.LocalDateTime.now;

import com.sewon.account.application.AccountService;
import com.sewon.account.model.Account;
import com.sewon.asset.dto.properties.AssetProperties;
import com.sewon.asset.dto.properties.AssetSearchProperties;
import com.sewon.asset.dto.result.AllAssetResult;
import com.sewon.asset.dto.result.AssetResult;
import com.sewon.asset.dto.result.RentalResult;
import com.sewon.asset.model.Asset;
import com.sewon.asset.repository.AssetRepository;
import com.sewon.asset.repository.AssetSearchRepository;
import com.sewon.assetlocation.application.AssetLocationService;
import com.sewon.assetlocation.exception.LocationErrorCode;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assettype.application.AssetTypeService;
import com.sewon.assettype.exception.AssetTypeErrorCode;
import com.sewon.assettype.model.AssetType;
import com.sewon.barcode.model.Barcode;
import com.sewon.common.exception.DomainException;
import com.sewon.inbound.application.AssetInboundService;
import com.sewon.inbound.constant.InboundType;
import com.sewon.inbound.model.AssetInbound;
import com.sewon.outbound.application.AssetOutboundService;
import com.sewon.outbound.constant.OutboundType;
import com.sewon.outbound.model.AssetOutbound;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetSearchRepository assetSearchRepository;
    private final AccountService accountService;
    private final AssetTypeService assetTypeService;
    private final AssetInboundService assetInboundService;
    private final AssetOutboundService assetOutboundService;
    private final AssetLocationService assetLocationService;


    @Transactional
    public Asset registerAsset(AssetProperties properties, Long id) {
        AssetLocation assetLocation = assetLocationService.findAssetLocationById(
            properties.getLocationId());

        AssetType assetType = assetTypeService.findAssetByParentAndChildType
            (properties.getParentTypeId(),
                properties.getChildTypeId());

        Account account = accountService.findAccountById(id);
        Asset asset = properties.toAsset(assetType, account, assetLocation);
        Barcode.createBarcode(asset);
        Asset assetPs = assetRepository.save(asset);

        assetInboundService.registerAssetInbound(
            AssetInbound.of(InboundType.getByAssetStatus(assetPs.getAssetStatusValue()),
                assetPs.getCreatedDate(), account, assetPs,
                assetLocation));

        return assetPs;
    }

    @Transactional
    public List<Asset> registerAssetList(List<AssetProperties> properties, Long id) {
        List<Asset> assets = new ArrayList<>();

        Map<Long, AssetLocation> assetLocationMap = cachingAssetLocation(
            assetLocationService.findAllAssetLocation());

        Map<String, AssetType> assetTypeMap =
            cachingAssetType(assetTypeService.findAll());

        if (assetLocationMap.isEmpty()) {
            throw new DomainException(LocationErrorCode.LOCATION_NOT_FOUND);
        }
        if (assetTypeMap.isEmpty()) {
            throw new DomainException(AssetTypeErrorCode.ASSET_TYPE_NOT_FOUND);
        }

        Account account = accountService.findAccountById(id);
        for (AssetProperties assetProperties : properties) {

            AssetLocation assetLocation = assetLocationMap.get(assetProperties.getLocationId());

            AssetType assetType = assetTypeMap.get(
                assetProperties.getParentTypeId() + "-" + assetProperties.getChildTypeId());
            Asset asset = assetProperties.toAsset(assetType, account, assetLocation);
            assets.add(asset);
            Barcode.createBarcode(asset);
        }
        List<Asset> assetsPs = assetRepository.saveAll(assets);
        List<AssetInbound> assetInbounds = new ArrayList<>();
        assetsPs.forEach(asset -> assetInbounds.add(
            AssetInbound.of(InboundType.getByAssetStatus(asset.getAssetStatusValue()),
                asset.getCreatedDate(), account, asset,
                asset.getAssetLocation())
        ));
        assetInboundService.registerAllAssetInbound(assetInbounds);
        return assetsPs;
    }

    private Map<String, AssetType> cachingAssetType(List<AssetType> assetTypes) {
        return assetTypes.stream()
            .collect(
                Collectors.toMap(
                    a -> a.getParentCategory().getId() + "-" + a.getChildCategory().getId(),
                    Function.identity()));
    }

    private Map<Long, AssetLocation> cachingAssetLocation(List<AssetLocation> locations) {
        return locations.stream()
            .collect(Collectors.toMap(AssetLocation::getId, Function.identity()));
    }

    @Transactional
    public void updateAsset(AssetProperties properties, String barcode) {
        Asset asset = assetRepository.findAssetByBarcode(barcode)
            .orElseThrow(() -> new DomainException(ASSET_NOT_FOUND));
        AssetLocation assetLocation = assetLocationService.findAssetLocationById(
            properties.getLocationId());

        AssetType assetType = assetTypeService.findAssetTypeById(properties.getChildTypeId());

        assetRepository.save(properties.updateAsset(asset, assetLocation, assetType));
    }

    @Transactional
    public void transferAsset(Long assetId, Long fromLocationId, Long toLocationId,
        Long accountId) {
        Asset asset = findAssetById(assetId);
        AssetLocation fromLocation = assetLocationService.findAssetLocationById(fromLocationId);
        if (asset.isEnableTransferLocation(fromLocation)) {
            AssetLocation toLocation = assetLocationService.findAssetLocationById(toLocationId);
            Account account = accountService.findAccountById(accountId);
            assetInboundService.registerAssetInbound(
                AssetInbound.of(InboundType.TRANSFER, now(), account, asset, toLocation)
            );
            assetOutboundService.registerAssetOutbound(
                AssetOutbound.of(OutboundType.TRANSFER, now(), account, asset, fromLocation)
            );
            asset.setAssetLocation(toLocation);
            return;
        }
        throw new DomainException(ASSET_NOT_FOUND);
    }

    @Transactional
    public List<String> disposeAsset(List<String> barcodes) {
        return assetRepository.findAllAssetByBarcode(barcodes)
            .stream()
            .map(asset -> {
                asset.dispose();
                return asset.getBarcodeValue();
            }).toList();
    }


    @Transactional
    public void deleteAllAssetById(List<Long> ids) {
        assetRepository.deleteAllById(ids);
    }

    public Asset findAssetById(Long id) {
        return assetRepository.findById(id)
            .orElseThrow(() -> new DomainException(ASSET_NOT_FOUND));
    }

    public AssetResult findAssetResultById(Long id) {
        return AssetResult.from(assetRepository.findById(id)
            .orElseThrow(() -> new DomainException(ASSET_NOT_FOUND))
        );
    }

    public AllAssetResult findAssetByBarcode(String value) {
        return assetRepository.findAssetDtoByBarcode(value)
            .orElseThrow(() -> new DomainException(ASSET_NOT_FOUND));
    }

    public List<AllAssetResult> findAllAsset(int size) {
        return assetRepository.findAll(size);
    }

    public List<AllAssetResult> findAllByCondition(AssetSearchProperties properties) {
        return assetSearchRepository.searchAssets(properties);
    }

    public List<RentalResult> findAllRentalEnableAssetByCondition(
        AssetSearchProperties properties) {
        return assetSearchRepository.searchRentalEnableAssets(properties);
    }


}
