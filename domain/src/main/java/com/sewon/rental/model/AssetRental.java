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
import com.sewon.rental.constant.RentalStatus;
import com.sewon.rental.converter.RentalStatusConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
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

    @ManyToOne(targetEntity = AssetInbound.class, fetch = LAZY, cascade = {CascadeType.PERSIST,
        CascadeType.MERGE})
    @JoinColumn(name = "asset_inbound_id")
    private AssetInbound assetInbound;

    @ManyToOne(targetEntity = AssetOutbound.class, fetch = LAZY, cascade = {CascadeType.PERSIST,
        CascadeType.MERGE})
    @JoinColumn(name = "asset_outbound_id")
    private AssetOutbound assetOutbound;

    @Convert(converter = RentalStatusConverter.class)
    @Column(name = "status", nullable = false)
    private RentalStatus rentalStatus;

    @Column(name = "rented_at", nullable = false)
    private LocalDate rentalDate;

    @Column(name = "from_at", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_at", nullable = false)
    private LocalDate toDate;

    @Column(name = "returned_at")
    private LocalDate returnDate;


    public static AssetRental of(Account account, Asset asset, AssetLocation assetLocation,
        AssetInbound assetInbound, AssetOutbound assetOutbound, RentalStatus status,
        LocalDate rentalDate,
        LocalDate fromDate, LocalDate toDate, LocalDate returnDate) {
        return new AssetRental(null, account, asset, assetLocation, assetInbound, assetOutbound,
            status, rentalDate, fromDate, toDate, returnDate);
    }

    public static AssetRental request(Account account, Asset asset, AssetLocation assetLocation,
        RentalStatus status, LocalDate rentalDate, LocalDate fromDate, LocalDate toDate,
        LocalDate returnDate) {
        return of(account, asset, assetLocation, null, null,
            status, rentalDate, fromDate, toDate, returnDate);
    }


    public void inbounded(AssetInbound inbound) {
        this.assetInbound = inbound;
    }

    public void outbounded(AssetOutbound outbound) {
        this.assetOutbound = outbound;
    }

    public void rentalApprove() {
        this.rentalStatus = RentalStatus.RENT;
    }

    public void requestRentalReturn() {
        this.rentalStatus = RentalStatus.REQUEST_RETURN;
    }

    public void returnApprove() {
        this.rentalStatus = RentalStatus.RETURN;
    }

    public void returnRequestCancel() {
        if (RentalStatus.REQUEST_RETURN.equals(this.rentalStatus)) {
            this.rentalStatus = RentalStatus.RENT;
        }
    }

    public void rentalExpire() {
        this.rentalStatus = RentalStatus.EXPIRE;
    }

    public boolean isExpire() {
        return LocalDate.now().isAfter(this.toDate);
    }


    public String getBarcodeValue() {
        return asset.getBarcodeValue();
    }

    public String getCorporation() {
        return asset.getCorporation();
    }

    public String getDepartment() {
        return asset.getDepartment();
    }

    public String getAssetLocation() {
        return asset.getLocation();
    }

    public AssetLocation getToLocation() {
        return assetLocation;
    }

    public AssetLocation getFromLocation() {
        return asset.getAssetLocation();
    }

    public String getRentLocation() {
        return assetLocation.getLocation();
    }

    public String getParentCategory() {
        return asset.getParentCategory();
    }

    public String getChildCategory() {
        return asset.getChildCategory();
    }

    public String getRenter() {
        return account.getName();
    }

    public String getRegister() {
        return asset.getAccountName();
    }

    public Long getLocationAffiliationId() {
        return asset.getAssetLocation().getAffiliation().getId();
    }

    public Long getAccountAffiliationId() {
        return account.getAffiliation().getId();
    }

}
