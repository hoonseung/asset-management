package com.sewon.rental.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.account.model.Account;
import com.sewon.asset.model.Asset;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.common.model.BaseTime;
import com.sewon.inbound.model.AssetInbound;
import com.sewon.outbound.model.AssetOutbound;
import jakarta.persistence.Column;
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

@SQLDelete(sql = "UPDATE asset_rental SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetRentalDeletedFilter", autoEnabled = true)
@Filter(name = "assetRentalDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "asset_rental")
@Entity
public class AssetRental extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Account.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(targetEntity = Asset.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(targetEntity = AssetLocation.class, optional = false)
    @JoinColumn(name = "asset_location_id", nullable = false)
    private AssetLocation assetLocation;

    @ManyToOne(targetEntity = AssetInbound.class, fetch = LAZY)
    @JoinColumn(name = "asset_inbound_id")
    private AssetInbound assetInbound;

    @ManyToOne(targetEntity = AssetOutbound.class, fetch = LAZY)
    @JoinColumn(name = "asset_outbound_id")
    private AssetOutbound assetOutbound;

    @Column(name = "rented_at", nullable = false)
    private LocalDateTime rentalDate;

    @Column(name = "returned_at")
    private LocalDateTime returnDate;
}
