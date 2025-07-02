package com.sewon.dsl;

import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sewon.affiliation.model.QAffiliation;
import com.sewon.asset.dto.AssetResult;
import com.sewon.asset.dto.AssetSearchProperties;
import com.sewon.asset.model.Asset;
import com.sewon.asset.model.QAsset;
import com.sewon.asset.repository.AssetSearchRepository;
import com.sewon.assetlocation.model.QAssetLocation;
import com.sewon.assettype.model.QAssetType;
import com.sewon.corporation.model.QCorporation;
import com.sewon.dsl.dto.AssetQueryResponseDto;
import com.sewon.dsl.dto.QAssetQueryResponseDto;
import com.sewon.rental.constant.RentalStatus;
import com.sewon.rental.model.QAssetRental;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class AssetDynamicSearchRepository implements AssetSearchRepository {

    private final JPAQueryFactory queryFactory;
    private final QAsset defaultAsset = QAsset.asset;

    @Override
    public List<AssetResult> searchAssets(AssetSearchProperties properties) {
        QAsset asset = new QAsset(PathMetadataFactory.forVariable("asset"),
            // 해당 엔티티들 한번에 초기화 하기 위해 연관 엔티티 지정
            new PathInits("barcode", "assetLocation.affiliation.corporation",
                "assetType.assetType", "account"));
        QAffiliation affiliation = asset.assetLocation.affiliation;
        QCorporation corporation = asset.assetLocation.affiliation.corporation;
        return queryFactory
            .select(new QAssetQueryResponseDto(
                asset.id,
                asset.barcode.value,
                asset.assetLocation.affiliation.corporation.name,
                asset.assetLocation.affiliation.department,
                asset.assetLocation.location,
                asset.assetDivision,
                asset.assetType.id,
                asset.assetType.assetType.name,
                asset.assetType.name,
                asset.assetStatus,
                asset.manufacturer,
                asset.model,
                asset.acquisitionDate,
                asset.acquisitionPrice,
                asset.account.name
            ))
            .from(asset)
            .join(asset.barcode)
            .join(asset.assetLocation)
            .join(affiliation)
            .join(corporation)
            .join(asset.assetType)
            .join(asset.account)
            .where(
                eqLocation(properties.locationId()),
                eqParentAndChildeType(properties.parentTypeId(), properties.childTypeId()),
                betweenDate(properties.after(), properties.before())
            ).limit(properties.size())
            .fetch()
            .stream()
            .map(this::from)
            .toList();
    }

    @Override
    public List<AssetResult> searchRentalEnableAssets(AssetSearchProperties properties) {
        QAssetRental assetRental = QAssetRental.assetRental;
        JPAQuery<Asset> query = queryFactory
            .selectFrom(defaultAsset)
            .leftJoin(defaultAsset.barcode).fetchJoin()
            .leftJoin(assetRental).on(assetRental.asset.eq(defaultAsset))
            .where(
                eqLocation(properties.locationId()),
                eqParentAndChildeType(properties.parentTypeId(), properties.childTypeId()),
                enableRental(assetRental)
            );
        if (properties.size() > 0) {
            query = query.limit(properties.size());
        }
        return query
            .fetch()
            .stream()
            .map(AssetResult::from)
            .toList();
    }

    private BooleanExpression eqLocation(Long locationId) {
        QAssetLocation assetLocation = defaultAsset.assetLocation;
        return locationId != null ? assetLocation.id.eq(locationId) : null;
    }

    private BooleanExpression eqParentAndChildeType(Long parentTypeId, Long childTypeId) {
        QAssetType assetType = defaultAsset.assetType;
        if (parentTypeId != null && childTypeId != null) {
            return assetType.id.eq(childTypeId).and(assetType.assetType.id.eq(parentTypeId));
        } else if (parentTypeId != null) {
            return eqParentTypeId(parentTypeId);
        } else {
            return eqChildTypeId(childTypeId);
        }
    }

    private BooleanExpression betweenDate(LocalDate after, LocalDate before) {
        if (after != null && before != null) {
            return defaultAsset.createdDate.between(after.atStartOfDay(),
                before.atTime(LocalTime.MAX));
        } else if (after != null) {
            return defaultAsset.createdDate.goe(after.atStartOfDay());
        } else if (before != null) {
            return defaultAsset.createdDate.loe((before.atTime(LocalTime.MAX)));
        } else {
            return null;
        }
    }

    private BooleanExpression eqParentTypeId(Long id) {
        return id != null ? defaultAsset.assetType.assetType.id.eq(id) : null;
    }

    private BooleanExpression eqChildTypeId(Long id) {
        return id != null ? defaultAsset.assetType.id.eq(id) : null;
    }

    private BooleanExpression enableRental(QAssetRental assetRental) {
        return assetRental.id.isNull()
            .or(assetRental.rentalStatus.notIn(RentalStatus.REQUEST_RENTAL, RentalStatus.RENT,
                RentalStatus.REQUEST_RETURN, RentalStatus.EXPIRE));
    }

    public AssetResult from(AssetQueryResponseDto dto) {
        return AssetResult.of(
            dto.getId(),
            dto.getBarcode(),
            dto.getCorporationName(),
            dto.getDepartment(),
            dto.getLocation(),
            dto.getDivision().getDescription(),
            dto.getAssetTypeId(),
            dto.getParentCategory(),
            dto.getChildCategory(),
            dto.getStatus().getDescription(),
            dto.getManufacturer(),
            dto.getModel(),
            dto.getAcquisitionDate().toLocalDate(),
            dto.getAcquisitionPrice(),
            dto.getRegisterName()
        );
    }


}
