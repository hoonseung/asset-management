package com.sewon.stocktaking.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.account.model.Account;
import com.sewon.common.model.BaseTime;
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

@SQLDelete(sql = "UPDATE asset_stock_taking SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetStockTakingDeletedFilter", autoEnabled = true)
@Filter(name = "assetStockTakingDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "asset_stock_taking")
@Entity
public class AssetStockTaking extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "auditng_at", nullable = false)
    private LocalDateTime auditingDate;

    @Column(name = "completed_at")
    private LocalDateTime completedDate;

    @ManyToOne(targetEntity = Account.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
