package com.sewon.dsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sewon.asset.dto.AssetSearchProperties;
import com.sewon.asset.model.Asset;
import com.sewon.asset.model.QAsset;
import com.sewon.asset.repository.AssetSearchRepository;
import com.sewon.assetlocation.model.QAssetLocation;
import com.sewon.assettype.model.QAssetType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AssetDynamicSearchRepository implements AssetSearchRepository {

    private final JPAQueryFactory queryFactory;
    private final QAsset asset = QAsset.asset;

    @Override
    public List<Asset> searchAssets(AssetSearchProperties properties) {
        return queryFactory
            .selectFrom(asset)
            .where(
                eqLocation(properties.locationId()),
                eqParentAndChildeType(properties.parentTypeId(), properties.childTypeId()),
                betweenDate(properties.after(), properties.before())
            ).limit(properties.size())
            .fetch();
    }

    private BooleanExpression eqLocation(Long locationId) {
        QAssetLocation assetLocation = asset.assetLocation;
        return locationId != null ? assetLocation.id.eq(locationId) : null;
    }

    private BooleanExpression eqParentAndChildeType(Long parentTypeId, Long childTypeId) {
        QAssetType assetType = asset.assetType;
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

    private BooleanExpression eqParentTypeId(Long id) {
        return id != null ? asset.assetType.assetType.id.eq(id) : null;
    }

    private BooleanExpression eqChildTypeId(Long id) {
        return id != null ? asset.assetType.id.eq(id) : null;
    }


}
