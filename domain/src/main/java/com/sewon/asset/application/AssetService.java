package com.sewon.asset.application;

import static com.sewon.asset.exception.AssetErrorCode.ASSET_NOT_FOUND;

import com.sewon.account.application.AccountService;
import com.sewon.account.model.Account;
import com.sewon.affiliation.application.AffiliationService;
import com.sewon.affiliation.model.Affiliation;
import com.sewon.asset.dto.AssetProperties;
import com.sewon.asset.model.Asset;
import com.sewon.asset.repository.AssetRepository;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assettype.application.AssetTypeService;
import com.sewon.assettype.model.AssetType;
import com.sewon.barcode.model.Barcode;
import com.sewon.common.exception.DomainException;
import com.sewon.inbound.application.AssetInboundService;
import com.sewon.inbound.constant.InboundType;
import com.sewon.inbound.model.AssetInbound;
import java.time.LocalDate;
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
    private final AccountService accountService;
    private final AffiliationService affiliationService;
    private final AssetTypeService assetTypeService;
    private final AssetInboundService assetInboundService;


    @Transactional
    public Asset registerAsset(AssetProperties properties, Long id) {
        Affiliation affiliation = affiliationService.findAffiliationByDepartmentAndCorporation(
            properties.getDepartment(),
            properties.getCorporation());

        AssetLocation assetLocation = affiliation.findLocation(properties.getLocation());

        AssetType assetType = assetTypeService.findAssetByParentAndChildType
            (properties.getParentType(),
                properties.getChildType());

        Account account = accountService.findAccountById(id);

        Asset asset = assetRepository.save(properties.toAsset(assetType, account, assetLocation));

        assetInboundService.registerAssetInbound(
            AssetInbound.of(InboundType.getByAssetStatus(asset.getAssetStatusValue()),
                asset.getCreatedDate(), account, asset,
                assetLocation));
        Barcode.createBarcode(asset);
        return asset;
    }

    @Transactional
    public List<Asset> registerAssetList(List<AssetProperties> properties, Long id) {
        List<Asset> assets = new ArrayList<>();

        Map<String, Affiliation> affiliationMap = cachingAffiliation(
            affiliationService.findAllAffiliation());

        Map<String, AssetType> assetTypeMap =
            cachingAssetType(assetTypeService.findAll());

        Account account = accountService.findAccountById(id);
        for (AssetProperties assetProperties : properties) {

            Affiliation affiliation = affiliationMap.get(
                assetProperties.getCorporation() + "-" + assetProperties.getDepartment());
            AssetLocation assetLocation = affiliation.findLocation(assetProperties.getLocation());

            AssetType assetType = assetTypeMap.get(
                assetProperties.getParentType() + "-" + assetProperties.getChildType());
            Asset asset = assetProperties.toAsset(assetType, account, assetLocation);
            assets.add(asset);
        }
        List<Asset> assetsPs = assetRepository.saveAll(assets);
        assetsPs.forEach(asset -> {
            assetInboundService.registerAssetInbound(
                AssetInbound.of(InboundType.getByAssetStatus(asset.getAssetStatusValue()),
                    asset.getCreatedDate(), account, asset,
                    asset.getAssetLocation()));
            Barcode.createBarcode(asset);
        });
        return assetsPs;
    }

    private Map<String, Affiliation> cachingAffiliation(List<Affiliation> affiliations) {
        return affiliations.stream()
            .collect(Collectors.toMap(a -> a.getCorporation() + "-" + a.getDepartment(),
                Function.identity()));
    }

    private Map<String, AssetType> cachingAssetType(List<AssetType> assetTypes) {
        return assetTypes.stream()
            .collect(
                Collectors.toMap(a -> a.getParentCategoryName() + "-" + a.getChildCategoryName(),
                    Function.identity()));
    }

    @Transactional
    public void deleteAssetById(Long id) {
        assetRepository.deleteById(id);
    }

    public Asset findAssetById(Long id) {
        return assetRepository.findById(id)
            .orElseThrow(() -> new DomainException(ASSET_NOT_FOUND));
    }

    public Asset findAssetByBarcode(String value) {
        return assetRepository.findByBarcode(value)
            .orElseThrow(() -> new DomainException(ASSET_NOT_FOUND));
    }

    public List<Asset> findAllAsset() {
        return assetRepository.findAll();
    }

    public List<Asset> findAllAsset(int size) {
        return assetRepository.findAll(size);
    }

    public List<Asset> findAllAssetByLocation(String location, int size) {
        return assetRepository.findAllByLocation(location, size);
    }

    public List<Asset> findAllByLocationAndChildType(String location, String type, int size) {
        return assetRepository.findAllByLocationAndChildType(location, type, size);
    }

    public List<Asset> findAllByLocationAndChildTypeBetween(String location, String type,
        LocalDate after, LocalDate before, int size) {
        return assetRepository.findAllByLocationAndChildTypeBetween(
            location,
            type,
            after.atStartOfDay(),
            before.atTime(23, 59, 59, 999_999),
            size
        );
    }

    public List<Asset> findAllByLocationAndParentType(String location, String type, int size) {
        return assetRepository.findAllByLocationAndParentType(location, type, size);
    }

    public List<Asset> findAllByLocationAndParentTypeBetween(String location, String type,
        LocalDate after, LocalDate before, int size) {
        return assetRepository.findAllByLocationAndParentTypeBetween(
            location,
            type,
            after.atStartOfDay(),
            before.atTime(23, 59, 59, 999_999),
            size
        );
    }

    public List<Asset> findAllBetween(LocalDate after, LocalDate before, int size) {
        return assetRepository.findAllBetween(
            after.atStartOfDay(),
            before.atTime(23, 59, 59, 999_999),
            size
        );
    }


}
