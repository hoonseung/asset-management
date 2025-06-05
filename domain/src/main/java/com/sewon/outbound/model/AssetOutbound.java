package com.sewon.outbound.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.account.model.Account;
import com.sewon.asset.model.Asset;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.common.model.BaseTime;
import com.sewon.outbound.constant.OutboundType;
import com.sewon.outbound.converter.OutboundTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE asset_outbound SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetOutboundDeletedFilter", autoEnabled = true)
@Filter(name = "assetOutboundDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "asset_outbound")
@Entity
public class AssetOutbound extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Convert(converter = OutboundTypeConverter.class)
    @Column(name = "outbound_type", nullable = false)
    private OutboundType outboundType;

    @Column(name = "outbound_at", nullable = false)
    private LocalDateTime outboundDate;

    @ManyToOne(targetEntity = Account.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(targetEntity = Asset.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(targetEntity = AssetLocation.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_location_id", nullable = false)
    private AssetLocation assetLocation;


    public static AssetOutbound of(OutboundType outboundType, LocalDateTime outboundDate,
        Account account,
        Asset asset, AssetLocation assetLocation) {
        return new AssetOutbound(null, outboundType, outboundDate, account, asset, assetLocation);
    }
}
