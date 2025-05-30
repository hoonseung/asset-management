package com.sewon.inbound.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.account.model.Account;
import com.sewon.asset.model.Asset;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.common.model.BaseTime;
import com.sewon.inbound.constant.InboundType;
import com.sewon.inbound.converter.InboundTypeConverter;
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

@SQLDelete(sql = "UPDATE asset_inbound SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetInboundDeletedFilter", autoEnabled = true)
@Filter(name = "assetInboundDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "asset_inbound")
@Entity
public class AssetInbound extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Convert(converter = InboundTypeConverter.class)
    @Column(name = "inbounded_type", nullable = false)
    private InboundType inboundType;

    @Column(name = "inbounded_at", nullable = false)
    private LocalDateTime inboundDate;

    @ManyToOne(targetEntity = Account.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(targetEntity = Asset.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(targetEntity = AssetLocation.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_location_id", nullable = false)
    private AssetLocation assetLocation;


    public static AssetInbound of(InboundType inboundType, LocalDateTime inboundDate,
        Account account,
        Asset asset, AssetLocation assetLocation) {
        return new AssetInbound(null, inboundType, inboundDate, account, asset, assetLocation);
    }
}
