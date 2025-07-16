package com.sewon.asset.model;

import static jakarta.persistence.DiscriminatorType.STRING;
import static jakarta.persistence.FetchType.EAGER;
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
import com.sewon.barcode.model.Barcode;
import com.sewon.common.model.BaseTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE asset SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetDeletedFilter", autoEnabled = true)
@Filter(name = "assetDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter
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

    @OneToOne(mappedBy = "asset", cascade = CascadeType.ALL, fetch = EAGER)
    private Barcode barcode;


    public static Asset of(AssetDivision assetDivision, AssetStatus assetStatus,
        String manufacturer, String model, Integer acquisitionPrice, LocalDateTime acquisitionDate,
        AssetType assetType, Account account, AssetLocation assetLocation) {
        return new Asset(
            null,
            assetDivision,
            assetStatus,
            manufacturer,
            model,
            acquisitionPrice,
            acquisitionDate,
            assetType,
            account,
            assetLocation,
            null);
    }

    public static Asset of(Long id, AssetDivision assetDivision, AssetStatus assetStatus,
        String manufacturer, String model, Integer acquisitionPrice, LocalDateTime acquisitionDate,
        AssetType assetType, Account account, AssetLocation assetLocation, Barcode barcode) {
        return new Asset(
            id,
            assetDivision,
            assetStatus,
            manufacturer,
            model,
            acquisitionPrice,
            acquisitionDate,
            assetType,
            account,
            assetLocation,
            barcode);
    }

    public void dispose() {
        this.assetStatus = AssetStatus.UNUSED;
    }

    public Long getAssetTypeId() {
        return this.assetType.getId();
    }

    public Long getAssetLocationId() {
        return this.assetLocation.getId();
    }

    public String getBarcodeValue() {
        return this.barcode.getValue();
    }

    public String getCorporation() {
        return this.assetLocation.getCorporation();
    }

    public String getDepartment() {
        return this.assetLocation.getDepartment();
    }

    public String getLocation() {
        return this.assetLocation.getLocation();
    }

    public String getDivision() {
        return this.assetDivision.getDescription();
    }

    public String getStatus() {
        return this.assetStatus.getDescription();
    }

    public String getParentCategory() {
        return this.assetType.getParentCategory().getName();
    }

    public String getChildCategory() {
        return this.assetType.getChildCategory().getName();
    }

    public String getAccountName() {
        return this.account.getName();
    }

    public int getAssetStatusValue() {
        return this.assetStatus.getValue();
    }

    public boolean isEnableTransferLocation(AssetLocation location) {
        return getAssetLocation().getId().equals(location.getId());
    }

    public Long getAffiliationId() {
        return assetLocation.getAffiliation().getId();
    }
}
