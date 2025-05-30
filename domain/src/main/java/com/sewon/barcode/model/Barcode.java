package com.sewon.barcode.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.asset.model.Asset;
import com.sewon.common.model.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE barcode SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "barcodeDeletedFilter", autoEnabled = true)
@Filter(name = "barcodeDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "barcode")
@Entity
public class Barcode extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value", nullable = false, unique = true)
    private String value;

    @OneToOne(targetEntity = Asset.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;


    public static Barcode createBarcode(Asset asset) {
        Barcode barcode = new Barcode(null, createBarcodeValue(asset), asset);
        asset.setBarcode(barcode);
        return barcode;
    }

    public static String createBarcodeValue(Asset asset) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String datePrefix = formatter.format(LocalDate.now());
        String assetDelimiterValue = "A" + asset.getId();
        String assetTypeDelimiterValue = "T" + asset.getAssetTypeId();
        String locationDelimiterValue = "L" + asset.getAssetLocationId();
        return String.join("", datePrefix, assetDelimiterValue,
            assetTypeDelimiterValue,
            locationDelimiterValue);
    }
}
