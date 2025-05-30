package com.sewon.stocktaking.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.account.model.Account;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.common.model.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE asset_stock_taking SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetStockTakingDeletedFilter", autoEnabled = true)
@Filter(name = "assetStockTakingDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "asset_stock_taking",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_location_id_auditing_at", columnNames = {"asset_location_id",
            "auditing_at"})
    })
@Entity
public class AssetStockTaking extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "auditing_at", nullable = false)
    private LocalDate auditingDate;

    @ManyToOne(targetEntity = Account.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToOne(targetEntity = AssetLocation.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_location_id", nullable = false)
    private AssetLocation assetLocation;

    public static AssetStockTaking of(LocalDate auditingDate, Account account,
        AssetLocation assetLocation) {
        return new AssetStockTaking(null, auditingDate, account, assetLocation);
    }
}
