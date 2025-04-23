package com.sewon.asset.model;

import static jakarta.persistence.DiscriminatorType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.account.model.Account;
import com.sewon.asset.constant.AssetDivision;
import com.sewon.asset.constant.AssetStatus;
import com.sewon.asset.converter.AssetDivisionConverter;
import com.sewon.asset.converter.AssetStatusConverter;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assettype.model.AssetType;
import com.sewon.common.model.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
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

@SQLDelete(sql = "UPDATE asset SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetDeletedFilter", autoEnabled = true)
@Filter(name = "assetDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "d_type", discriminatorType = STRING)
@Table(name = "asset")
@Entity
public class Asset extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Convert(converter = AssetDivisionConverter.class)
    @Column(name = "division", nullable = false)
    private AssetDivision assetDivision;

    @Convert(converter = AssetStatusConverter.class)
    @Column(name = "status", nullable = false)
    private AssetStatus assetStatus;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "acquisition_price", nullable = false)
    private Integer acquisitionPrice;

    @Column(name = "acquisition_date", nullable = false)
    private LocalDateTime acquisitionDate;

    @ManyToOne(targetEntity = AssetType.class, optional = false, fetch = LAZY)
    @JoinColumn(name = "asset_type_id", nullable = false)
    private AssetType assetType;

    @ManyToOne(targetEntity = Account.class, optional = false, fetch = LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(targetEntity = AssetLocation.class, optional = false, fetch = LAZY)
    @JoinColumn(name = "asset_location_id", nullable = false)
    private AssetLocation assetLocation;


}
