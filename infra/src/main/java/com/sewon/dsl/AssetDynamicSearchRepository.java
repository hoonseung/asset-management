package com.sewon.dsl;

import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sewon.account.model.QAccount;
import com.sewon.affiliation.model.QAffiliation;
import com.sewon.asset.dto.properties.AssetSearchProperties;
import com.sewon.asset.dto.result.AllAssetResult;
import com.sewon.asset.dto.result.RentalResult;
import com.sewon.asset.model.Asset;
import com.sewon.asset.model.QAsset;
import com.sewon.asset.model.QElectronicAsset;
import com.sewon.asset.repository.AssetSearchRepository;
import com.sewon.assetlocation.model.QAssetLocation;
import com.sewon.assettype.model.QAssetType;
import com.sewon.barcode.model.QBarcode;
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

    private final QAsset asset = new QAsset(PathMetadataFactory.forVariable("asset"),
        // 해당 엔티티들 한번에 초기화 하기 위해 연관 엔티티 지정
        new PathInits("barcode", "assetLocation.affiliation.corporation",
            "assetType.assetType", "account"));
    private final QElectronicAsset electronicAsset = new QElectronicAsset(
        PathMetadataFactory.forVariable("electronic"),
        new PathInits("barcode", "assetLocation.affiliation.corporation",
            "assetType.assetType", "account"));

    @Override
    public List<AllAssetResult> searchAssets(AssetSearchProperties properties) {
        return getAssetQuery(properties, asset, electronicAsset)
            .stream()
            .map(this::from)
            .toList()
            ;
    }

    private List<AssetQueryResponseDto> getAssetQuery(AssetSearchProperties properties,
        QAsset asset, QElectronicAsset electronicAsset) {
        QBarcode barcode = asset.barcode;
        QAssetLocation assetLocation = asset.assetLocation;
        QAffiliation affiliation = asset.assetLocation.affiliation;
        QCorporation corporation = asset.assetLocation.affiliation.corporation;
        QAssetType assetType = asset.assetType;
        QAccount account = asset.account;

        return queryFactory
            .select(new QAssetQueryResponseDto(
                asset.id,
                asset.barcode.value,
                asset.assetLocation.affiliation.corporation.name,
                asset.assetLocation.affiliation.department,
                asset.assetLocation.location,
                asset.assetDivision,
                asset.assetType,
                asset.assetType.id,
                asset.assetType.assetType.name,
                asset.assetType.name,
                asset.assetStatus,
                asset.manufacturer,
                asset.model,
                asset.acquisitionDate,
                asset.acquisitionPrice,
                asset.account.name,
                electronicAsset.cpu,
                electronicAsset.ram,
                electronicAsset.storage,
                electronicAsset.gpu,
                asset.createdDate
            ))
            .from(asset)
            .leftJoin(electronicAsset).on(electronicAsset.id.eq(asset.id))
            .join(barcode)
            .join(assetLocation)
            .join(affiliation)
            .join(corporation)
            .join(assetType)
            .join(account)
            .where(
                eqCorporation(corporation, properties.corporationId()),
                eqAffiliation(affiliation, properties.affiliationId()),
                eqLocation(assetLocation, properties.locationId()),
                eqParentAndChildeType(assetType, properties.parentTypeId(),
                    properties.childTypeId()),
                betweenDate(properties.after(), properties.before())
            ).limit(properties.size())
            .fetch();
    }


    @Override
    public List<RentalResult> searchRentalEnableAssets(AssetSearchProperties properties) {
        QAssetRental assetRental = QAssetRental.assetRental;
        QCorporation corporation = asset.assetLocation.affiliation.corporation;
        QAffiliation affiliation = asset.assetLocation.affiliation;
        QAssetLocation assetLocation = asset.assetLocation;
        QAssetType assetType = asset.assetType;

        QAssetRental joinRental = QAssetRental.assetRental;

        JPAQuery<Asset> query = queryFactory
            .selectFrom(asset)
            .leftJoin(asset.barcode).fetchJoin()
            .leftJoin(assetRental).on(assetRental.asset.eq(asset)
                .and(assetRental.createdDate.eq(
                    // asset_id eq 인 것 들 중 제일 최신 렌탈 레코드 가져오기
                    JPAExpressions.select(assetRental.createdDate.max())
                        .from(joinRental)
                        .where(joinRental.asset.id.eq(asset.id))
                )))
            .where(
                eqCorporation(corporation, properties.corporationId()),
                eqAffiliation(affiliation, properties.affiliationId()),
                eqLocation(assetLocation, properties.locationId()),
                eqParentAndChildeType(assetType, properties.parentTypeId(),
                    properties.childTypeId()),
                enableRental(assetRental)
            );
        if (properties.size() > 0) {
            query = query.limit(properties.size());
        }
        return query
            .fetch()
            .stream()
            .map(RentalResult::from)
            .toList();
    }

    // join에 참조 엔티티 넣어야함
    public BooleanExpression eqCorporation(QCorporation corporation, Long corporationId) {
        return corporationId != null ? corporation.id.eq(corporationId) : null;
    }

    // join에 참조 엔티티 넣어야함
    public BooleanExpression eqAffiliation(QAffiliation affiliation, Long affiliationId) {
        return affiliationId != null ? affiliation.id.eq(affiliationId) : null;
    }


    private BooleanExpression eqLocation(QAssetLocation assetLocation, Long locationId) {
        return locationId != null ? assetLocation.id.eq(locationId) : null;
    }

    private BooleanExpression eqParentAndChildeType(QAssetType assetType, Long parentTypeId,
        Long childTypeId) {
        if (parentTypeId != null && childTypeId != null) {
            return assetType.id.eq(childTypeId).and(assetType.assetType.id.eq(parentTypeId));
        } else if (parentTypeId != null) {
            return eqParentTypeId(assetType, parentTypeId);
        } else {
            return eqChildTypeId(assetType, childTypeId);
        }
    }

    private BooleanExpression betweenDate(LocalDate after, LocalDate before) {
        QAsset asset = QAsset.asset;
        if (after != null && before != null) {
            return asset.createdDate.between(after.atStartOfDay(),
                before.atTime(LocalTime.MAX));
        } else if (after != null) {
            return asset.createdDate.goe(after.atStartOfDay());
        } else if (before != null) {
            return asset.createdDate.loe((before.atTime(LocalTime.MAX)));
        } else {
            return null;
        }
    }

    private BooleanExpression eqParentTypeId(QAssetType assetType, Long id) {
        return id != null ? assetType.assetType.id.eq(id) : null;
    }

    private BooleanExpression eqChildTypeId(QAssetType assetType, Long id) {
        return id != null ? assetType.id.eq(id) : null;
    }

    private BooleanExpression enableRental(QAssetRental assetRental) {
        return assetRental.id.isNull()
            .or(assetRental.rentalStatus.notIn(RentalStatus.REQUEST_RENTAL, RentalStatus.RENT,
                RentalStatus.REQUEST_RETURN, RentalStatus.EXPIRE));
    }

    public AllAssetResult from(AssetQueryResponseDto dto) {
        return new AllAssetResult(
            dto.getId(),
            dto.getBarcode(),
            dto.getCorporationName(),
            dto.getDepartment(),
            dto.getLocation(),
            dto.getDivision(),
            dto.getAssetType(),
            dto.getAssetTypeId(),
            dto.getParentCategory(),
            dto.getChildCategory(),
            dto.getStatus(),
            dto.getManufacturer(),
            dto.getModel(),
            dto.getAcquisitionDate(),
            dto.getAcquisitionPrice(),
            dto.getRegisterName(),
            dto.getCpu(),
            dto.getRam(),
            dto.getStorage(),
            dto.getGpu(),
            dto.getRegistrationDate()
        );
    }
}



