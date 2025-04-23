package com.sewon.stocktaking.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.asset.model.Asset;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.common.model.BaseTime;
import com.sewon.stocktaking.constant.AssetCheckingStatus;
import com.sewon.stocktaking.constant.AssetStockTakingStatus;
import com.sewon.stocktaking.converter.AssetStockTakingStatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE asset_stock_taking_item SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetStockTakingItemDeletedFilter", autoEnabled = true)
@Filter(name = "assetStockTakingItemDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "asset_stock_taking_item",
    uniqueConstraints = @UniqueConstraint(name = "uk_asset_id_asset_stock_taking_id",
        columnNames = {"asset_id", "asset_stock_taking_id"}))
@Entity
public class AssetStockTakingItem extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Convert(converter = AssetStockTakingStatusConverter.class)
    @Column(name = "stock_taking_status", nullable = false)
    private AssetStockTakingStatus assetStockTakingStatus;

    @Column(name = "checked_at", nullable = false)
    private LocalDateTime checkedDate;

    @Convert(converter = AssetStockTakingStatusConverter.class)
    @Column(name = "checking_status", nullable = false)
    private AssetCheckingStatus assetCheckingStatus;

    @Column(name = "remark", length = 300)
    private String remark;

    @ManyToOne(targetEntity = Asset.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(targetEntity = AssetStockTaking.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_stock_taking_id", nullable = false)
    private AssetStockTaking assetStockTaking;

    @ManyToOne(targetEntity = AssetLocation.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_location_id", nullable = false)
    private AssetLocation assetLocation;
}
